package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.dailyScrum.request.UpdateDailyScrumRequest;
import com.starmix.checkmate.application.port.out.persistence.DailyScrumPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DailyScrumService {

    private final DailyScrumPersistencePort dailyScrumPersistencePort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final TaskPersistencePort taskPersistencePort;

    public List<DailyScrum> getDailyScrumsByProjectId(String projectId) {
        return dailyScrumPersistencePort.findAllByProjectId(projectId);
    }

    public DailyScrum getTodayDailyScrum(String projectId) {
        Optional<DailyScrum> dailyScrum = dailyScrumPersistencePort.findByTimestampAndProjectId(projectId, LocalDate.now());
        return dailyScrum.orElse(null);
    }

    public void createDailyScrum(String projectId, UpdateDailyScrumRequest request) {
        String email = jwtUtil.extractEmail();
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));

        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        if(!project.isMember(user)) {
            throw new CustomException("Permission Denied", HttpStatus.FORBIDDEN);
        }
        Optional<DailyScrum> dailyScrumOptional = dailyScrumPersistencePort.findByTimestampAndProjectId(projectId, LocalDate.now());

        if(dailyScrumOptional.isEmpty()) {
            DailyScrum dailyScrum = DailyScrum.create(projectId);
            dailyScrumPersistencePort.save(dailyScrum);
        } else {
            DailyScrum dailyScrum = dailyScrumOptional.get();
            List<Task> todoTasks = request.todoTaskIds().stream().map(
                    taskId -> taskPersistencePort.findById(taskId)
                            .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND))
            ).toList();
            List<Task> doneTasks = request.doneTaskIds().stream().map(
                    taskId -> taskPersistencePort.findById(taskId)
                            .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND))
            ).toList();

            dailyScrum.updateTasks(todoTasks, doneTasks, user);
            dailyScrumPersistencePort.save(dailyScrum);
        }
    }
}