package com.starmix.checkmate.adapter.in.rest.web.notification;

import com.starmix.checkmate.adapter.in.rest.web.notification.response.NotificationResponse;
import com.starmix.checkmate.application.service.NotificationService;
import com.starmix.checkmate.domain.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestParam(required = false) String projectId
    ) {
        List<Notification> notifications = notificationService.getNotifications(projectId);
        List<NotificationResponse> response = notifications.stream().map(NotificationResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{notificationId}/read")
    public ResponseEntity<Void> readNotification(
            @PathVariable String notificationId
    ) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable String notificationId
    ) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
