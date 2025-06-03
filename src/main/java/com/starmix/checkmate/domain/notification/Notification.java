package com.starmix.checkmate.domain.notification;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Notification {
    private String notificationId;
    private String userId;
    private String title;
    private String description;
    private String targetId;
    private Boolean isRead;
    private Project project;
}
