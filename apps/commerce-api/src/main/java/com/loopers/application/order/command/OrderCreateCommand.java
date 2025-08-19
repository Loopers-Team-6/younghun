package com.loopers.application.order.command;

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



