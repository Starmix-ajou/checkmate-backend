package com.starmix.checkmate.adapter.in.http.test;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.application.service.ProjectTestService;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final ProjectPersistencePort projectPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final JwtUtil jwtUtil;

    private final ProjectTestService projectService;
    private final SseEmitterManager sseEmitterManager;

    @PostMapping("/definition")
    public void createFeatureDefinition(@RequestBody CreateFeatureDefinitionRequest request) {
        var response = projectService.createFeatureDefinition(request);
        sseEmitterManager.sendEvent("create-feature-definition", response);
    }

    @PutMapping("/definition")
    public void feedbackFeatureDefinition(@RequestBody FeedbackRequest request) {
        var response = projectService.feedbackFeatureDefinition(request);
        sseEmitterManager.sendEvent("feedback-feature-definition", response);
    }

    @GetMapping("/specification")
    public void createFeatureSpecification() {
        var response = projectService.createFeatureSpecification();
        sseEmitterManager.sendEvent("create-feature-specification", response);
    }

    @PutMapping("/specification")
    public void feedbackFeatureSpecification(@RequestBody FeedbackRequest request) {
        var response = projectService.feedbackFeatureSpecification(request);
        sseEmitterManager.sendEvent("feedback-feature-specification", response);
    }

    @Transactional
    @PostMapping("/project/create")
    public ResponseEntity<Project> createProjectTest (
            @RequestBody CreateFeatureDefinitionRequest request
    ) {
        String email = jwtUtil.extractEmail();
        User leader = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        List<User> members = request.members().stream().map(
                member -> userPersistencePort.findByEmail(member.email())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND))
        ).toList();

        Project project = Project.createTemporaryProject(request, leader, members);
        leader.approve(project.getProjectId());

        userPersistencePort.save(leader);
        members.forEach(userPersistencePort::save);
        projectPersistencePort.save(project);
        return ResponseEntity.ok().body(project);
    }
}
