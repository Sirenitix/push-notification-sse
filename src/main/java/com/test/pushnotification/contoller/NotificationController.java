package com.test.pushnotification.contoller;


import com.test.pushnotification.payload.NotificationRequest;
import com.test.pushnotification.service.EmitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private EmitterService emitterService;

    @GetMapping("/subscription")
    public SseEmitter subsribe() {
        log.info("subscribing...");

        SseEmitter sseEmitter = new SseEmitter(24 * 60 * 60 * 1000L);
        emitterService.addEmitter(sseEmitter);

        log.info("subscribed");
        return sseEmitter;
    }

    @PostMapping("/notification/{username}")
    public ResponseEntity<?> send(@PathVariable String username, @RequestBody NotificationRequest request) {
        emitterService.pushNotification(username, request.getFrom(), request.getMessage());
        return ResponseEntity.ok().body("message pushed to user " + username);
    }
}
