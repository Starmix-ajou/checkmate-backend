package com.starmix.checkmate.adapter.out.ai.adapter;

import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.out.ai.client.AIFeignClient;
import com.starmix.checkmate.adapter.out.ai.client.request.*;
import com.starmix.checkmate.adapter.out.ai.client.response.*;
import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AIAdapter implements AIPort {

    private final AIFeignClient aiFeignClient;

    @Override
    public Suggestion createFunctionDefinition(Project project, String definitionUrl) {
        try {
            CreateFeatureDefinitionFeignRequest request = CreateFeatureDefinitionFeignRequest.builder()
                    .email(project.getLeader().getEmail())
                    .description(project.getDescription())
                    .definitionUrl(definitionUrl)
                    .build();
            CreateFeatureDefinitionFeignResponse response = aiFeignClient.createFeatureDefinition(request);
            return response.suggestion().toDomain();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public FeedbackDto feedbackFeatureDefinition(String email, String feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback)
                    .build();
            FeedbackFeatureDefinitionFeignResponse response = aiFeignClient.feedbackFeatureDefinition(request);
            return FeedbackDto.fromFeatureDefinition(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Feature> createFeatureSpecification(String email) {
        try {
            CreateFeatureSpecificationFeignRequest request = CreateFeatureSpecificationFeignRequest.builder()
                    .email(email)
                    .build();
            CreateFeatureSpecificationFeignResponse response = aiFeignClient.createFeatureSpecification(request);
            return response.features();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public FeedbackDto feedbackFeatureSpecification(String email, FeedbackFeatureSpecificationRequest feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback.feedback())
                    .createdFeatures(feedback.createdFeatures())
                    .modifiedFeatures(feedback.modifiedFeatures())
                    .deletedFeatures(feedback.deletedFeatures())
                    .build();
            FeedbackFeatureSpecificationFeignResponse response = aiFeignClient.feedbackFeatureSpecification(request);
            return FeedbackDto.fromFeatureSpecification(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CreateSprintFeignResponse createSprint(
            String projectId,
            List<String> pendingTaskIds,
            LocalDate startDate
    ) {
        try {
            CreateSprintFeignRequest request = CreateSprintFeignRequest.builder()
                    .projectId(projectId)
                    .pendingTaskIds(pendingTaskIds)
                    .startDate(startDate)
                    .build();
            return aiFeignClient.createSprint(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CreateMeetingFeignResponse saveMeeting(Meeting meeting) {
        try {
            CreateMeetingFeignRequest request = CreateMeetingFeignRequest.fromDomain(meeting);
            return aiFeignClient.createMeeting(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CreateActionItemsFeignResponse> createActionItems(String projectId, List<String> actionItems) {
        try {
            CreateActionItemsFeignRequest request = CreateActionItemsFeignRequest.from(projectId, actionItems);
            return aiFeignClient.createActionItems(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
