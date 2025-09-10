package com.loopers.domain;

import java.time.LocalDate;
import java.util.UUID;

public record RootMeticsMessage(
    String eventId,
    Long eventTime,
    BaseMessage payload
) implements RootMessage {

  public RootMeticsMessage(BaseMessage payload) {
    this(UUID.randomUUID().toString(), LocalDate.now().toEpochDay(), payload);
  }
}
