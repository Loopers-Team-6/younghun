//package com.loopers.interfaces.api;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/kafka")
//public class TestController {
//
//  @Autowired
//  private KafkaTemplate<String, String> kafkaTemplate;
//
//  @PostMapping("/send")
//  public ResponseEntity<String> sendMessage(
//      @RequestParam String topic,
//      @RequestParam String key,
//      @RequestParam String message) {
//
//    kafkaTemplate.send(topic, key, message);
//    return ResponseEntity.ok("Message sent to " + topic);
//  }
//}
