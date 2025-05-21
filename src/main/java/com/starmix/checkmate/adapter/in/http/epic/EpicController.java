package com.starmix.checkmate.adapter.in.http.epic;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
import com.starmix.checkmate.adapter.in.http.epic.response.EpicResponse;
import com.starmix.checkmate.application.service.EpicService;
import com.starmix.checkmate.domain.epic.Epic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/epic")
public class EpicController {

    private final EpicService epicService;

    @GetMapping
    public ResponseEntity<List<EpicResponse>> getEpics (
            @RequestParam String projectId,
            @RequestParam(required = false) String sprintId
    ) {
        List<EpicResponse> epics = epicService.getEpics(projectId, sprintId);
        return ResponseEntity.ok().body(epics);
    }

    @PostMapping
    public ResponseEntity<Void> createEpic(
            @RequestParam String projectId,
            @RequestBody CreateEpicRequest request
    ) {
        epicService.createEpic(projectId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{epicId}")
    public ResponseEntity<Void> deleteEpic(@PathVariable String epicId) {
        epicService.deleteEpic(epicId);
        return ResponseEntity.ok().build();
    }
}