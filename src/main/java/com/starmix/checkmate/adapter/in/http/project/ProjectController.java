package com.starmix.checkmate.adapter.in.http.project;

import com.starmix.checkmate.adapter.in.http.project.request.ApproveRequest;
import com.starmix.checkmate.adapter.in.http.project.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.http.project.response.ProjectsResponse;
import com.starmix.checkmate.application.service.ProjectService;
import com.starmix.checkmate.domain.project.Project;
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
            @PathVariable String id, @RequestBody ApproveRequest request
    ) {
        projectService.approve(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/deny")
    public ResponseEntity<Void> approve (
            @PathVariable String id
    ) {
        projectService.deny(id);
        return ResponseEntity.ok().build();
    }
}