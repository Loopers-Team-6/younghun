package com.loopers.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "messageType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = StockMetricsMessage.class, name = "STOCK_METRICS_MESSAGE"),
    @JsonSubTypes.Type(value = LikeMetricsMessage.class, name = "LIKE_METRICS_MESSAGE"),
    @JsonSubTypes.Type(value = ViewMetricsMessage.class, name = "VIEW_METRICS_MESSAGE")
})
public sealed interface BaseMessage permits StockMetricsMessage, LikeMetricsMessage, ViewMetricsMessage {

}
