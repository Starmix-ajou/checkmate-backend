package com.starmix.checkmate.domain.epic;

import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class Epic {
    private String epicId;
    private String title;
    private String description;
    private String projectId;
    private String featureId;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Epic create(
            String title, String description, String projectId
    ) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(title)
                .description(description)
                .projectId(projectId)
                .featureId(null)
                .build();
    }

    public static Epic fromFeature(Feature feature, String projectId) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(feature.getName())
                .description(feature.getUseCase())
                .projectId(projectId)
                .featureId(feature.getFeatureId())
                .build();
    }

    public void updateDates(List<Task> tasks) {
        LocalDate startDate = tasks.stream()
                .map(Task::getStartDate)
                .filter(Objects::nonNull)
                .min(LocalDate::compareTo)
                .orElse(null);

        LocalDate endDate = tasks.stream()
                .map(Task::getEndDate)
                .filter(Objects::nonNull)
                .max(LocalDate::compareTo)
                .orElse(null);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Sprint findCurrentSprint(List<Sprint> sprints) {
        LocalDate today = LocalDate.now();
        return sprints.stream()
                .filter(sprint -> !today.isBefore(sprint.getStartDate()) && !today.isAfter(sprint.getEndDate()))
                .findFirst()
                .orElse(null);
    }

    public Sprint findLatestSprint(List<Sprint> sprints) {
        return sprints.stream()
                .max(Comparator.comparing(Sprint::getSequence))
                .orElse(null);
    }
}
