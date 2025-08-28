package com.loopers.data_platform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockDataServer {

  @Async
  @EventListener
  public void send(PlatformSendEvent event) throws InterruptedException {
    log.info("대기중...");
    Thread.sleep(5000);
    log.info("{} 데이터가 플랫폼으로 전송되었습니다. id: {}, payload: {}", event.type(), event.id(), event.payload());
  }

}
