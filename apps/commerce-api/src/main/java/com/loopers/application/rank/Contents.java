package com.loopers.application.rank;

public record Contents(
    Long brandId,
    String brandName,
    Long productId,
    String productName,
    Integer todayRank,
    Integer diff,     // 순위 변화량 (양수=상승, 음수=하락)
    String status     // "UP", "DOWN", "SAME", "NEW"
) {
}
