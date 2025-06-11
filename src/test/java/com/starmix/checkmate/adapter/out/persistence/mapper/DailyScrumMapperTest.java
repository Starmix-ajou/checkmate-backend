package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.DailyScrumEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import com.starmix.checkmate.domain.task.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DailyScrumMapperTest {

    @Test
    @DisplayName("DailyScrumEntity -> DailyScrum 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate today = LocalDate.now();
        
        TaskEntity todoTaskEntity1 = mock(TaskEntity.class);
        TaskEntity todoTaskEntity2 = mock(TaskEntity.class);
        List<TaskEntity> todoTaskEntities = Arrays.asList(todoTaskEntity1, todoTaskEntity2);
        
        TaskEntity doneTaskEntity = mock(TaskEntity.class);
        List<TaskEntity> doneTaskEntities = Collections.singletonList(doneTaskEntity);
        
        Task todoTask1 = mock(Task.class);
        Task todoTask2 = mock(Task.class);
        Task doneTask = mock(Task.class);

        DailyScrumEntity dailyScrumEntity = DailyScrumEntity.builder()
                .id("daily-scrum-123")
                .timestamp(today)
                .todoTasks(todoTaskEntities)
                .doneTasks(doneTaskEntities)
                .projectId("project-123")
                .build();

        try (MockedStatic<TaskMapper> taskMapperMock = mockStatic(TaskMapper.class)) {
            // when
            taskMapperMock.when(() -> TaskMapper.toDomain(todoTaskEntity1)).thenReturn(todoTask1);
            taskMapperMock.when(() -> TaskMapper.toDomain(todoTaskEntity2)).thenReturn(todoTask2);
            taskMapperMock.when(() -> TaskMapper.toDomain(doneTaskEntity)).thenReturn(doneTask);
            
            DailyScrum dailyScrum = DailyScrumMapper.toDomain(dailyScrumEntity);

            // then
            assertThat(dailyScrum).isNotNull();
            assertThat(dailyScrum.getDailyScrumId()).isEqualTo(dailyScrumEntity.getId());
            assertThat(dailyScrum.getTimestamp()).isEqualTo(dailyScrumEntity.getTimestamp());
            assertThat(dailyScrum.getProjectId()).isEqualTo(dailyScrumEntity.getProjectId());
            
            assertThat(dailyScrum.getTodoTasks()).hasSize(2);
            assertThat(dailyScrum.getTodoTasks()).contains(todoTask1, todoTask2);
            
            assertThat(dailyScrum.getDoneTasks()).hasSize(1);
            assertThat(dailyScrum.getDoneTasks()).contains(doneTask);

            taskMapperMock.verify(() -> TaskMapper.toDomain(todoTaskEntity1));
            taskMapperMock.verify(() -> TaskMapper.toDomain(todoTaskEntity2));
            taskMapperMock.verify(() -> TaskMapper.toDomain(doneTaskEntity));
        }
    }

    @Test
    @DisplayName("DailyScrum 도메인 -> DailyScrumEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate today = LocalDate.now();
        
        Task todoTask1 = mock(Task.class);
        Task todoTask2 = mock(Task.class);
        List<Task> todoTasks = Arrays.asList(todoTask1, todoTask2);
        
        Task doneTask = mock(Task.class);
        List<Task> doneTasks = Collections.singletonList(doneTask);
        
        TaskEntity todoTaskEntity1 = mock(TaskEntity.class);
        TaskEntity todoTaskEntity2 = mock(TaskEntity.class);
        TaskEntity doneTaskEntity = mock(TaskEntity.class);

        DailyScrum dailyScrum = DailyScrum.builder()
                .dailyScrumId("daily-scrum-456")
                .timestamp(today)
                .todoTasks(todoTasks)
                .doneTasks(doneTasks)
                .projectId("project-456")
                .build();

        try (MockedStatic<TaskMapper> taskMapperMock = mockStatic(TaskMapper.class)) {
            // when
            taskMapperMock.when(() -> TaskMapper.toEntity(todoTask1)).thenReturn(todoTaskEntity1);
            taskMapperMock.when(() -> TaskMapper.toEntity(todoTask2)).thenReturn(todoTaskEntity2);
            taskMapperMock.when(() -> TaskMapper.toEntity(doneTask)).thenReturn(doneTaskEntity);
            
            DailyScrumEntity dailyScrumEntity = DailyScrumMapper.toEntity(dailyScrum);

            // then
            assertThat(dailyScrumEntity).isNotNull();
            assertThat(dailyScrumEntity.getId()).isEqualTo(dailyScrum.getDailyScrumId());
            assertThat(dailyScrumEntity.getTimestamp()).isEqualTo(dailyScrum.getTimestamp());
            assertThat(dailyScrumEntity.getProjectId()).isEqualTo(dailyScrum.getProjectId());
            
            assertThat(dailyScrumEntity.getTodoTasks()).hasSize(2);
            assertThat(dailyScrumEntity.getTodoTasks()).contains(todoTaskEntity1, todoTaskEntity2);
            
            assertThat(dailyScrumEntity.getDoneTasks()).hasSize(1);
            assertThat(dailyScrumEntity.getDoneTasks()).contains(doneTaskEntity);

            taskMapperMock.verify(() -> TaskMapper.toEntity(todoTask1));
            taskMapperMock.verify(() -> TaskMapper.toEntity(todoTask2));
            taskMapperMock.verify(() -> TaskMapper.toEntity(doneTask));
        }
    }

    @Test
    @DisplayName("빈 태스크 목록을 가진 데일리 스크럼 변환 테스트")
    void emptyTaskListsTest() {
        // given
        DailyScrumEntity entityWithEmptyLists = DailyScrumEntity.builder()
                .id("daily-scrum-empty")
                .timestamp(LocalDate.now())
                .todoTasks(Collections.emptyList())
                .doneTasks(Collections.emptyList())
                .projectId("project-empty")
                .build();

        DailyScrum domainWithEmptyLists = DailyScrum.builder()
                .dailyScrumId("daily-scrum-domain-empty")
                .timestamp(LocalDate.now())
                .todoTasks(Collections.emptyList())
                .doneTasks(Collections.emptyList())
                .projectId("project-domain-empty")
                .build();

        // when
        DailyScrum resultDomain = DailyScrumMapper.toDomain(entityWithEmptyLists);
        DailyScrumEntity resultEntity = DailyScrumMapper.toEntity(domainWithEmptyLists);

        // then
        assertThat(resultDomain.getTodoTasks()).isEmpty();
        assertThat(resultDomain.getDoneTasks()).isEmpty();

        assertThat(resultEntity.getTodoTasks()).isEmpty();
        assertThat(resultEntity.getDoneTasks()).isEmpty();
    }

    @Test
    @DisplayName("태스크 목록이 null인 데일리 스크럼 변환 테스트")
    void nullTaskListsTest() {
        // given
        DailyScrumEntity entityWithNullLists = DailyScrumEntity.builder()
                .id("daily-scrum-null-lists")
                .timestamp(LocalDate.now())
                .todoTasks(null)
                .doneTasks(null)
                .projectId("project-null-lists")
                .build();

        DailyScrum domainWithNullLists = DailyScrum.builder()
                .dailyScrumId("daily-scrum-domain-null-lists")
                .timestamp(LocalDate.now())
                .todoTasks(null)
                .doneTasks(null)
                .projectId("project-domain-null-lists")
                .build();

        // when & then
        assertThatThrownBy(() -> DailyScrumMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> DailyScrumMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("create 메소드로 생성된 DailyScrum 변환 테스트")
    void createMethodTest() {
        // given
        String projectId = "project-create";
        DailyScrum createdDailyScrum = DailyScrum.create(projectId);

        // when
        DailyScrumEntity entity = DailyScrumMapper.toEntity(createdDailyScrum);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getProjectId()).isEqualTo(projectId);
        assertThat(entity.getTimestamp()).isEqualTo(LocalDate.now());
        assertThat(entity.getTodoTasks()).isEmpty();
        assertThat(entity.getDoneTasks()).isEmpty();
    }

    @Test
    @DisplayName("null 데일리 스크럼 변환 테스트")
    void nullDailyScrumTest() {
        // then
        assertThatThrownBy(() -> DailyScrumMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> DailyScrumMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}