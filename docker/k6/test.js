import http from 'k6/http';
import {sleep} from 'k6';
import {Counter, Rate, Trend} from 'k6/metrics';

// ---- Metrics 정의 ----
function defineApiMetrics(name) {
    return {
        rps: new Counter(`${name}_requests`),
        duration: new Trend(`${name}_duration`, true), // 응답 시간
        errors: new Rate(`${name}_errors`),
    };
}

const metrics = {
    view: defineApiMetrics("api_products_view"),
    like: defineApiMetrics("api_like_post"),
    unlike: defineApiMetrics("api_like_delete"),
    order: defineApiMetrics("api_orders"),
    payment: defineApiMetrics("api_payment"),
};

export const options = {
    vus: 100,
    iterations: 100,
    thresholds: {
        "api_orders_errors": ["rate<0.05"],        // 에러율 < 5%
        "api_orders_duration": ["p(95)<1000", "p(99)<2000"], // 응답시간 p95 < 500ms, p99 < 1s
    },
};

// ---- 유틸 함수 ----
function randomUserId(vu) {
    return `user-${vu}-${Math.floor(Math.random() * 100000)}`;
}

function randomProductId() {
    return Math.floor(Math.random() * 100) + 1; // 1~100
}

function randomQuantity() {
    return Math.floor(Math.random() * 3) + 1; // 1~3
}

// ---- API 호출 래퍼 ----
function trackRequest(api, fn) {
    const res = fn();
    metrics[api].rps.add(1);
    metrics[api].duration.add(res.timings.duration);
    metrics[api].errors.add(res.status >= 400);
    return res;
}

// ---- API 시나리오 ----
function doView(productId) {
    return trackRequest("view", () =>
        http.get(`http://localhost:8080/api/v1/products/${productId}`)
    );
}

function doLike(userId, productId) {
    const res = trackRequest("like", () =>
        http.post(`http://localhost:8080/api/v1/like/products/${productId}`, null, {
            headers: { 'X-USER-ID': userId },
        })
    );

    if (Math.random() > 0.5) {
        trackRequest("unlike", () =>
            http.del(`http://localhost:8080/api/v1/like/products/${productId}`, null, {
                headers: { 'X-USER-ID': userId },
            })
        );
    }
    return res;
}

function doOrder(userId, productId) {
    const orderPayload = JSON.stringify({
        address: 'address3',
        items: [{ productId: productId, quantity: randomQuantity() }],
        memo: '집앞에 두세요',
    });

    const orderRes = trackRequest("order", () =>
        http.post('http://localhost:8080/api/v1/orders', orderPayload, {
            headers: { 'Content-Type': 'application/json', 'X-USER-ID': userId },
        })
    );

    if (orderRes.status === 200) {
        const orderData = orderRes.json().data;
        const orderNumber = orderData.orderNumber;

        const paymentPayload = JSON.stringify({
            orderNumber: orderNumber,
            paymentMethod: 'CARD',
            cardInfo: { cardType: 'KB', cardNo: '1234-1234-1234-1234' },
            pgUserId: userId,
            payment: 2000000,
            description: '결제 충동',
        });

        trackRequest("payment", () =>
            http.post('http://localhost:8080/api/v1/payment', paymentPayload, {
                headers: { 'Content-Type': 'application/json', 'X-USER-ID': userId },
            })
        );
    }
}

export default function () {
    const userId = randomUserId(__VU);

    // View: 항상 5~10회 호출
    const viewCount = Math.floor(Math.random() * 6) + 5;
    for (let i = 0; i < viewCount; i++) {
        doView(randomProductId());
        sleep(Math.random() * 0.5);
    }

    // Like / Order: 선택적 호출
    if (Math.random() > 0.5) {
        doLike(userId, randomProductId());
        sleep(Math.random() * 2);
    }

    if (Math.random() > 0.5) {
        doOrder(userId, randomProductId());
        sleep(Math.random() * 2);
    }
}
