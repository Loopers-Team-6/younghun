package com.loopers.infrastructure.log;

import com.loopers.domain.log.EventLog;
import com.loopers.domain.log.EventLogRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EventLogRepositoryImpl implements EventLogRepository {
  private final EventLogJpaRepository repository;

  public EventLogRepositoryImpl(EventLogJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public void insert(String userId, String message) {
    repository.save(new EventLog(userId, message));
  }
}
