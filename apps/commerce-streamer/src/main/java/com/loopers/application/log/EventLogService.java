package com.loopers.application.log;

import com.loopers.domain.log.EventLogRepository;
import org.springframework.stereotype.Service;

@Service
public class EventLogService {
  private final EventLogRepository repository;

  public EventLogService(EventLogRepository repository) {
    this.repository = repository;
  }

  public void insert(String userId, String message) {
    repository.insert(userId, message);
  }
}
