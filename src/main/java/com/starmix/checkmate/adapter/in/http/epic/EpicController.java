package com.starmix.checkmate.adapter.in.http.epic;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
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

    @PostMapping
    public ResponseEntity<Void> createEpic(@RequestBody CreateEpicRequest request) {
        epicService.createEpic(request);
        return ResponseEntity.ok().body(null);
    }
}