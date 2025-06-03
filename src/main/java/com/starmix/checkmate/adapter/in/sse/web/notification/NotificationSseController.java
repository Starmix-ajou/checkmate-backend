package com.starmix.checkmate.adapter.in.sse.web.notification;

import com.starmix.checkmate.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse/notification")
public class NotificationSseController {
    private final NotificationService notificationService;

    @GetMapping
    public void getNotifications(@RequestParam(required = false) String projectId) {
        notificationService.getNotifications(projectId);
    }
}
