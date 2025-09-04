package com.loopers.application.metrics;

public interface MetricsStrategy {

  void process(long productId, int value);

  MetricsMethod method();
}
