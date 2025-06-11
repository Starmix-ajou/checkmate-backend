package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.FeatureEntity;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.task.Priority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeatureMapperTest {

    @Test
    @DisplayName("FeatureEntity -> Feature 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        FeatureEntity featureEntity = FeatureEntity.builder()
                .id("feature-entity-id")
                .featureId("feature-123")
                .name("테스트 기능")
                .useCase("테스트 사용 사례")
                .input("테스트 입력")
                .output("테스트 출력")
                .preCondition("테스트 선행 조건")
                .postCondition("테스트 후행 조건")
                .expectedDays(5)
                .startDate(startDate)
                .endDate(endDate)
                .difficulty(3)
                .priority(2)
                .projectId("project-123")
                .build();

        // when
        Feature feature = FeatureMapper.toDomain(featureEntity);

        // then
        assertThat(feature).isNotNull();
        assertThat(feature.getFeatureId()).isEqualTo(featureEntity.getId());  // 실제 구현은 id를 featureId로 매핑
        assertThat(feature.getName()).isEqualTo(featureEntity.getName());
        assertThat(feature.getUseCase()).isEqualTo(featureEntity.getUseCase());
        assertThat(feature.getInput()).isEqualTo(featureEntity.getInput());
        assertThat(feature.getOutput()).isEqualTo(featureEntity.getOutput());
        assertThat(feature.getPreCondition()).isEqualTo(featureEntity.getPreCondition());
        assertThat(feature.getPostCondition()).isEqualTo(featureEntity.getPostCondition());
        assertThat(feature.getExpectedDays()).isEqualTo(featureEntity.getExpectedDays());
        assertThat(feature.getStartDate()).isEqualTo(featureEntity.getStartDate());
        assertThat(feature.getEndDate()).isEqualTo(featureEntity.getEndDate());
        assertThat(feature.getDifficulty()).isEqualTo(featureEntity.getDifficulty());
        assertThat(feature.getPriority()).isEqualTo(Priority.getPriority(featureEntity.getPriority()));
        assertThat(feature.getProjectId()).isEqualTo(featureEntity.getProjectId());
    }

    @Test
    @DisplayName("Feature 도메인 -> FeatureEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        Feature feature = Feature.builder()
                .featureId("feature-456")
                .name("새 기능")
                .useCase("새 사용 사례")
                .input("새 입력")
                .output("새 출력")
                .preCondition("새 선행 조건")
                .postCondition("새 후행 조건")
                .expectedDays(7)
                .startDate(startDate)
                .endDate(endDate)
                .difficulty(4)
                .priority(Priority.HIGH)
                .projectId("project-456")
                .build();

        // when
        FeatureEntity featureEntity = FeatureMapper.toEntity(feature);

        // then
        assertThat(featureEntity).isNotNull();
        assertThat(featureEntity.getId()).isNull();  // toEntity에서는 id를 설정하지 않음
        assertThat(featureEntity.getFeatureId()).isEqualTo(feature.getFeatureId());
        assertThat(featureEntity.getName()).isEqualTo(feature.getName());
        assertThat(featureEntity.getUseCase()).isEqualTo(feature.getUseCase());
        assertThat(featureEntity.getInput()).isEqualTo(feature.getInput());
        assertThat(featureEntity.getOutput()).isEqualTo(feature.getOutput());
        assertThat(featureEntity.getPreCondition()).isEqualTo(feature.getPreCondition());
        assertThat(featureEntity.getPostCondition()).isEqualTo(feature.getPostCondition());
        assertThat(featureEntity.getExpectedDays()).isEqualTo(feature.getExpectedDays());
        assertThat(featureEntity.getStartDate()).isEqualTo(feature.getStartDate());
        assertThat(featureEntity.getEndDate()).isEqualTo(feature.getEndDate());
        assertThat(featureEntity.getDifficulty()).isEqualTo(feature.getDifficulty());
        assertThat(featureEntity.getPriority()).isEqualTo(feature.getPriority().getPriorityNum());
        assertThat(featureEntity.getProjectId()).isEqualTo(feature.getProjectId());
    }

    @Test
    @DisplayName("모든 Priority 값에 대한 변환 테스트")
    void priorityConversionTest() {
        // given
        FeatureEntity lowEntity = FeatureEntity.builder()
                .id("feature-low")
                .priority(100)
                .build();

        FeatureEntity mediumEntity = FeatureEntity.builder()
                .id("feature-medium")
                .priority(200)
                .build();

        FeatureEntity highEntity = FeatureEntity.builder()
                .id("feature-high")
                .priority(300)
                .build();

        // when
        Feature lowFeature = FeatureMapper.toDomain(lowEntity);
        Feature mediumFeature = FeatureMapper.toDomain(mediumEntity);
        Feature highFeature = FeatureMapper.toDomain(highEntity);

        // then
        assertThat(lowFeature.getPriority()).isEqualTo(Priority.LOW);
        assertThat(mediumFeature.getPriority()).isEqualTo(Priority.MEDIUM);
        assertThat(highFeature.getPriority()).isEqualTo(Priority.HIGH);

        // 반대 방향도 테스트
        assertThat(FeatureMapper.toEntity(lowFeature).getPriority()).isEqualTo(lowFeature.getPriority().getPriorityNum());
        assertThat(FeatureMapper.toEntity(mediumFeature).getPriority()).isEqualTo(mediumFeature.getPriority().getPriorityNum());
        assertThat(FeatureMapper.toEntity(highFeature).getPriority()).isEqualTo(highFeature.getPriority().getPriorityNum());
    }

    @Test
    @DisplayName("null 기능 변환 테스트")
    void nullFeatureTest() {
        // given & when & then
        assertThatThrownBy(() -> FeatureMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> FeatureMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}