package com.starmix.checkmate.adapter.in.http.project;

import com.starmix.checkmate.application.service.ProjectService;
import com.starmix.checkmate.domain.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectHttpController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects () {
        List<Project> sprints = projectService.getProjects();
        return ResponseEntity.ok().body(sprints);
    }
}