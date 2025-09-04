package com.loopers.domain.log;

public interface EventLogRepository {

  void insert(String userId, String message);
}
