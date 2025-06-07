package com.starmix.checkmate.adapter.in.rest.web.notification;

import com.starmix.checkmate.adapter.in.rest.web.notification.response.NotificationResponse;
import com.starmix.checkmate.application.service.NotificationService;
import com.starmix.checkmate.domain.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
            @RequestParam(required = false) String projectId,
            Integer page, Integer size
    ) {
        Page<Notification> notifications = notificationService.getNotifications(projectId, page, size);
        Page<NotificationResponse> response = notifications.map(NotificationResponse::from);
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
