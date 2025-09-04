package com.loopers.domain.log;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_log")
public class EventLog extends BaseEntity {
  private String userId;
  private String log;

  protected EventLog() {
  }

  public EventLog(String userId, String log) {
    this.userId = userId;
    this.log = log;
  }

  public String getLog() {
    return log;
  }

  public void setLog(String log) {
    this.log = log;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
