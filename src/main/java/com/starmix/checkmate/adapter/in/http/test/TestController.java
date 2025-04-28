package com.starmix.checkmate.adapter.in.http.test;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
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

        Project project = request.toDomain(leader, members);
        project.createProject(leader, members);

        userPersistencePort.save(leader);
        members.forEach(userPersistencePort::save);
        projectPersistencePort.save(project);
        return ResponseEntity.ok().body(project);
    }
}
