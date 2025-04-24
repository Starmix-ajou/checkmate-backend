package com.starmix.checkmate.adapter.in.http.project;

import com.starmix.checkmate.adapter.in.http.project.request.ApproveRequest;
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
    public ResponseEntity<List<Project>> getProjects () {
        List<Project> sprints = projectService.getProjects();
        return ResponseEntity.ok().body(sprints);
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