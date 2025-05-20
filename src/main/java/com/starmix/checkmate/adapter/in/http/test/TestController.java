package com.starmix.checkmate.adapter.in.http.test;

import com.starmix.checkmate.adapter.in.http.test.request.CreateSprintTestRequest;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
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

    @Transactional
    @PostMapping("/sprint")
    public ResponseEntity<Sprint> createSprintTest (
            @RequestBody CreateSprintTestRequest request
    ) {
        Integer sequence = sprintPersistencePort.getNextSequence(request.projectId());
        Sprint sprint =  Sprint.create(
                request.title(), request.description(), sequence,
                request.projectId(), request.startDate(), request.endDate()
        );
        sprintPersistencePort.save(sprint);
        return ResponseEntity.ok().body(sprint);
    }
}
