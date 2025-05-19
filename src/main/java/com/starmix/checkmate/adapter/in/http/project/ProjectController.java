package com.starmix.checkmate.adapter.in.http.project;

import com.starmix.checkmate.adapter.in.common.UserDto;
import com.starmix.checkmate.adapter.in.http.project.request.InviteProjectRequest;
import com.starmix.checkmate.adapter.in.http.project.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.http.project.request.UpdateMemberRequest;
import com.starmix.checkmate.adapter.in.http.project.request.UpdateProjectRequest;
import com.starmix.checkmate.adapter.in.http.project.response.ProjectsResponse;
import com.starmix.checkmate.application.service.ProjectService;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectsResponse>> getProjects (
            @RequestParam(required = false) ProjectStatus status
    ) {
        List<ProjectsResponse> projects = projectService.getProjects(status);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProjects (
            @PathVariable String id
    ) {
        Project project = projectService.getProject(id);
        return ResponseEntity.ok().body(project);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve (
            @PathVariable String id
    ) {
        projectService.approve(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/deny")
    public ResponseEntity<Void> deny (
            @PathVariable String id
    ) {
        projectService.deny(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(
            @PathVariable String projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        projectService.updateProject(projectId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/member")
    public ResponseEntity<Void> invite(
            @PathVariable String projectId,
            @RequestBody InviteProjectRequest request
    ) {
        projectService.invite(projectId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{projectId}/member/{memberId}")
    public ResponseEntity<Void> updateMember(
            @PathVariable String projectId,
            @PathVariable String memberId,
            @RequestBody UpdateMemberRequest request
    ) {
        projectService.updateMember(projectId, memberId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/member/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable String projectId,
            @PathVariable String memberId
    ) {
        projectService.deleteMember(projectId, memberId);
        return ResponseEntity.ok().build();
    }
}