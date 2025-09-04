package com.loopers.support.shared;

import java.time.LocalDate;
import java.util.UUID;

public record Message(
    String eventId,
    MessageType type,
    String payload,
    Long eventTime
) {

  public Message(String type, String payload) {
    this(UUID.randomUUID().toString(), MessageType.valueOf(type), payload, LocalDate.now().toEpochDay());
  }
}

enum MessageType {
  METRICS, EVICT, LOG
}
