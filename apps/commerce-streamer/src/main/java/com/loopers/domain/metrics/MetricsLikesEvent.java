package com.loopers.domain.metrics;

import java.util.Map;

public record MetricsLikesEvent(Map<Long, Long> map) {
}
