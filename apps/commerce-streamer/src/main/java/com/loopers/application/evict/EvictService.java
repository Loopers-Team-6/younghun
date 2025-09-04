package com.loopers.application.evict;

import com.loopers.domain.evict.EvictMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Service;

@Service
public class EvictService {
  private final MessageConvert convert;
  private final EvictCacheRepository repository;


  public EvictService(MessageConvert convert, EvictCacheRepository repository) {
    this.convert = convert;
    this.repository = repository;
  }

  public void evict(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    EvictMessage result = convert.convert(convertMessage.getPayload(), EvictMessage.class);
    repository.evict(result.key());
  }
}
