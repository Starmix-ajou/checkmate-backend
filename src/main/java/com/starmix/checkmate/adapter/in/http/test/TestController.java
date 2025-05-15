package com.starmix.checkmate.adapter.in.http.test;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
import com.starmix.checkmate.adapter.in.http.test.request.CreateSprintTestRequest;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.application.service.EpicService;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final SprintPersistencePort sprintPersistencePort;
    private final EpicService epicService;

    @Transactional
    @PostMapping("/sprint/create")
    public ResponseEntity<Sprint> createSprintTest (
            @RequestBody CreateSprintTestRequest request
    ) {
        Integer sequence = sprintPersistencePort.getNextSequence();
        Sprint sprint = request.toDomain(sequence);
        sprintPersistencePort.save(sprint);
        return ResponseEntity.ok().body(sprint);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> createEpic(@RequestBody CreateEpicRequest request) {
        epicService.createEpic(request);
        return ResponseEntity.ok().body(null);
    }
}
