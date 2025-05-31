package com.starmix.checkmate.adapter.in.rest.manager.project;

import com.starmix.checkmate.adapter.in.rest.common.response.ProjectBriefResponse;
import com.starmix.checkmate.adapter.in.rest.common.response.ProjectStatisticsResponse;
import com.starmix.checkmate.adapter.in.rest.common.response.ProjectUserResponse;
import com.starmix.checkmate.adapter.in.rest.common.request.ProjectStatus;
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
@RequestMapping("/manager/project")
public class ProjectManagerController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectsResponse>> getProjects (
            @RequestParam(required = false) ProjectStatus status
    ) {
        List<ProjectsResponse> projects =
                projectService.getProjects(status, Role.PRODUCT_MANAGER);
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

    @GetMapping("/{projectId}/member")
    public ResponseEntity<ProjectUserResponse> getMembers (
            @PathVariable String projectId
    ) {
        ProjectUserResponse response = projectService.getMembers(projectId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{projectId}/statistics")
    public ResponseEntity<ProjectStatisticsResponse> getProjectStatistics (
            @PathVariable String projectId
    ) {
        ProjectStatisticsResponse response =
                projectService.getProjectStatistics(projectId, Role.PRODUCT_MANAGER);
        return ResponseEntity.ok().body(response);
    }
}