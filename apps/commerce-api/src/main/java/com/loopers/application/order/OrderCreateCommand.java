package com.loopers.application.order;

import java.math.BigInteger;
import java.util.List;

public record OrderCreateCommand(
    String userId,
    String address,
    BigInteger usePoint,
    List<OrderItemCommands> items,
    String memo
) {
}



