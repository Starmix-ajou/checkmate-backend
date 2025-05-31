package com.starmix.checkmate.adapter.in.rest.web.project;

import com.starmix.checkmate.adapter.in.rest.common.response.ProjectBriefResponse;
import com.starmix.checkmate.adapter.in.rest.common.response.ProjectStatisticsResponse;
import com.starmix.checkmate.adapter.in.rest.common.response.ProjectUserResponse;
import com.starmix.checkmate.adapter.in.rest.web.project.request.InviteProjectRequest;
import com.starmix.checkmate.adapter.in.rest.common.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.rest.web.project.request.UpdateMemberRequest;
import com.starmix.checkmate.adapter.in.rest.web.project.request.UpdateProjectRequest;
import com.starmix.checkmate.adapter.in.rest.common.response.ProjectsResponse;
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
        List<ProjectsResponse> projects =
                projectService.getProjects(status, Role.DEVELOPER);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProject (
            @PathVariable String id
    ) {
        Project project = projectService.getProject(id);
        return ResponseEntity.ok().body(project);
    }

    @GetMapping("{id}/brief")
    public ResponseEntity<ProjectBriefResponse> getProjectBrief (
            @PathVariable String id
    ) {
        ProjectBriefResponse response = projectService.getProjectBrief(id);
        return ResponseEntity.ok().body(response);
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

    @GetMapping("/{projectId}/member")
    public ResponseEntity<ProjectUserResponse> getMembers (
            @PathVariable String projectId
    ) {
        ProjectUserResponse response = projectService.getMembers(projectId);
        return ResponseEntity.ok().body(response);
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

    @GetMapping("/{projectId}/statistics")
    public ResponseEntity<ProjectStatisticsResponse> getProjectStatistics (
            @PathVariable String projectId
    ) {
        ProjectStatisticsResponse response =
                projectService.getProjectStatistics(projectId, Role.DEVELOPER);
        return ResponseEntity.ok().body(response);
    }
}