package com.loopers.support.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;


public class Message {
    private final String eventId;
    private final MessageType type;
    private final String payload;
    private final LocalDate eventTime;

    // Jackson이 사용할 기본 생성자
    @JsonCreator
    public Message(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("type") String type,
        @JsonProperty("payload") String payload,
        @JsonProperty("eventTime") Long eventTime
    ) {
        this.eventId = eventId;
        this.type = MessageType.valueOf(type);
        this.payload = payload;
        this.eventTime = LocalDate.ofEpochDay(eventTime);
    }
    
    // 편의를 위한 생성자
    public Message(String eventId, MessageType type, String payload, LocalDate eventTime) {
        this.eventId = eventId;
        this.type = type;
        this.payload = payload;
        this.eventTime = eventTime;
    }

  public String getEventId() {
    return eventId;
  }

  public MessageType getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }

  public LocalDate getEventTime() {
    return eventTime;
  }
}

enum MessageType {
    METRICS, EVICT, LOG
}
