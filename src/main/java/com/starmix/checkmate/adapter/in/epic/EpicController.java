package com.starmix.checkmate.adapter.in.epic;

import com.starmix.checkmate.application.service.EpicService;
import com.starmix.checkmate.domain.epic.Epic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/epic")
public class EpicController {

    private final EpicService epicService;

    @GetMapping
    public ResponseEntity<List<Epic>> getEpicsByProjectId (
            @RequestParam String projectId
    ) {
        List<Epic> epics = epicService.getEpicsByProjectId(projectId);
        return ResponseEntity.ok().body(epics);
    }
}