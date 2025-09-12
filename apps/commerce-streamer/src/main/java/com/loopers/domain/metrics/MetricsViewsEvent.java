package com.loopers.domain.metrics;

import java.util.Map;

public record MetricsViewsEvent(Map<Long, Long> map) {
}
