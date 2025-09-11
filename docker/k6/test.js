import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 100, // 동시에 100명
    iterations: 100, // 유저별 1세션
};

function randomUserId(vu) {
    return `user-${vu}-${Math.floor(Math.random() * 100000)}`;
}

function randomProductId() {
    return Math.floor(Math.random() * 100) + 1; // 1~100
}

function randomQuantity() {
    return Math.floor(Math.random() * 3) + 1; // 1~3
}

function doView(productId) {
    const res = http.get(`http://host.docker.internal:8080/api/v1/products/${productId}`);
    check(res, { 'view success': (r) => r.status === 200 });
}

function doLike(userId, productId) {
    const res = http.post(
        `http://host.docker.internal:8080/api/v1/like/products/${productId}`,
        null,
        { headers: { 'X-USER-ID': userId } }
    );
    check(res, { 'like success': (r) => r.status === 200 || r.status === 201 });

    if (Math.random() > 0.5) {
        const resDel = http.del(
            `http://host.docker.internal:8080/api/v1/like/products/${productId}`,
            null,
            { headers: { 'X-USER-ID': userId } }
        );
        check(resDel, { 'unlike success': (r) => r.status === 200 });
    }
}

function doOrder(userId, productId) {
    const orderPayload = JSON.stringify({
        address: 'address3',
        items: [
            { productId: productId, quantity: randomQuantity() }
        ],
        memo: '집앞에 두세요',
    });

    const orderRes = http.post(
        'http://host.docker.internal:8080/api/v1/orders',
        orderPayload,
        {
            headers: {
                'Content-Type': 'application/json',
                'X-USER-ID': userId,
            },
        }
    );

    if (orderRes.status === 200) {
        const orderData = orderRes.json().data;
        const orderNumber = orderData.orderNumber;

        const paymentPayload = JSON.stringify({
            orderNumber: orderNumber,
            paymentMethod: 'CARD',
            cardInfo: {
                cardType: 'KB',
                cardNo: '1234-1234-1234-1234',
            },
            pgUserId: userId,
            payment: 2000000,
            description: '결제 충동',
        });

        const payRes = http.post(
            'http://host.docker.internal:8080/api/v1/payment',
            paymentPayload,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-USER-ID': userId,
                },
            }
        );
        check(payRes, { 'payment success': (r) => r.status === 200 });
    }
}

export default function () {
    const url = 'http://host.docker.internal:8080/api/v1/products'; // 엔드포인트 주소
    const res = http.get(url);

    // 응답 검증
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1); // 요청 간 대기 시간
}
