package com.loopers.application.order;

import java.util.List;

public record OrderCreateCommand(
    String userId,
    String address,
    List<ItemCommands> items,
    String memo
) {
}

record ItemCommands(
    Long productId,
    Long quantity
) {
}

