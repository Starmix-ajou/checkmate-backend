package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.domain.epic.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EpicMapperTest {

    @Test
    @DisplayName("EpicEntity -> Epic 도메인 변환 테스트")
    void toDomainTest() {
        // given
        EpicEntity epicEntity = EpicEntity.builder()
                .id("epic-123")
                .title("테스트 에픽")
                .description("에픽 설명")
                .projectId("project-123")
                .featureId("feature-123")
                .build();

        // when
        Epic epic = EpicMapper.toDomain(epicEntity);

        // then
        assertThat(epic).isNotNull();
        assertThat(epic.getEpicId()).isEqualTo(epicEntity.getId());
        assertThat(epic.getTitle()).isEqualTo(epicEntity.getTitle());
        assertThat(epic.getDescription()).isEqualTo(epicEntity.getDescription());
        assertThat(epic.getProjectId()).isEqualTo(epicEntity.getProjectId());
        assertThat(epic.getFeatureId()).isEqualTo(epicEntity.getFeatureId());
    }

    @Test
    @DisplayName("Epic 도메인 -> EpicEntity 변환 테스트")
    void toEntityTest() {
        // given
        Epic epic = Epic.builder()
                .epicId("epic-456")
                .title("새 에픽")
                .description("새 에픽 설명")
                .projectId("project-456")
                .featureId("feature-456")
                .build();

        // when
        EpicEntity epicEntity = EpicMapper.toEntity(epic);

        // then
        assertThat(epicEntity).isNotNull();
        assertThat(epicEntity.getId()).isEqualTo(epic.getEpicId());
        assertThat(epicEntity.getTitle()).isEqualTo(epic.getTitle());
        assertThat(epicEntity.getDescription()).isEqualTo(epic.getDescription());
        assertThat(epicEntity.getProjectId()).isEqualTo(epic.getProjectId());
        assertThat(epicEntity.getFeatureId()).isEqualTo(epic.getFeatureId());
    }

    @Test
    @DisplayName("null 에픽 변환 테스트")
    void nullEpicTest() {
        // then
        assertThatThrownBy(() -> EpicMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> EpicMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}