# checkmate-backend

![체크메이트썸네일](https://i.imgur.com/rpwI0gG.png)

- 🌟 Starmix Organization GitHub : [https://github.com/Starmix-ajou](https://github.com/Starmix-ajou)
- ♟ checkmate URL : [https://checkmate.it.kr](https://checkmate.it.kr)
- 🖥 Project Manager 전용 뷰 : [https://manager.checkmate.it.kr](https://manager.checkmate.it.kr)
- 📘 API Docs : [https://api.checkmate.it.kr](https://api.checkmate.it.kr/swagger-ui/index.html#/)

## 팀원 구성

<div align="center">

| 조성연                                                                                                                |
| --------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/user-attachments/assets/ab48c42a-9bdc-4081-8d1f-d1443d034c26" width="200" height="200"/> |
| <div align="center">[GitHub](https://github.com/yeonnnnjs)</div>                                                      |

</div>
<br>

## 프로젝트 소개

> **checkmate는 소규모 주니어 개발팀을 위한 프로젝트 관리 및 협업툴입니다.**

**AI**를 활용한 **프로젝트 생성**과 **Sprint 구성**, **회의록 자동 요약** 기능을 제공하여 팀의 초기 기획부터 실행까지의 과정을 효율적으로 지원합니다.<br>
회의 내용을 실시간으로 정리할 수 있는 공동 편집 기능을 통해 **주요 논의 사항을 요약**하고, 이를 실행 가능한 **액션 아이템(Task)으로 전환**할 수 있습니다.<br>
Task는 Epic 단위로 구조화할 수 있으며, Gantt Chart, Kanban Board, Calendar를 통해 관리할 수 있습니다.<br>
또한 상세 Task 페이지의 댓글 기능을 통해 팀 내부 이해관계자 간의 원활한 소통이 가능하도록 하여, 개발 과정 전반에서 협업의 생산성을 높입니다.

## 기능 시연
| Solution 1. AI 기반 Sprint Backlog 자동 생성으로 초기 계획 수립 |
| :---: |
| ![sol1](https://github.com/user-attachments/assets/c0d85375-7321-4e42-94fe-e1a0c5c48d0a) |

| Solution 2. 시각화된 프로젝트 진행률 및 작업 상태 공유 |
| :---: |
| ![sol2](https://github.com/user-attachments/assets/07f58706-ca44-4589-8fa7-636d0c5710c9) |

| Solution 3. AI 기반 회의록 요약 및 액션 아이템 도출 후 적용 |
| :---: |
| ![sol3](https://github.com/user-attachments/assets/787590d6-8947-47a2-9fc0-2c541d3898f2) |

| Solution 4. AI 기반 Sprint 자동 재구성으로 Task 우선순위 조정 |
| :---: |
| ![sol4](https://github.com/user-attachments/assets/dccba468-7885-43f4-8b8d-95c39e19c869) |

| Solution 5. Burndown Chart 및 프로젝트 진행 현황 공유 |
| :---: |
| ![sol5](https://github.com/user-attachments/assets/a1421cd4-c4bd-4991-b168-825c44e55b19) |

## 1. 개발 환경

### Backend
| Java | Spring | Spring<br>Boot | Spring<br>Security | JWT | Spring Cloud<br>OpenFeign
| :---: | :---: | :---: | :---: | :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/0af70e4e-1991-4da1-9eb8-94ee8ab51b73" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/90ee6491-df94-43cd-8ae0-f06816f8e6e3" alt="icon" width="65" height="65" /></div> | <img src="https://t1.daumcdn.net/cfile/tistory/27034D4F58E660F616" width="65" height="65" > | <img src="https://github.com/user-attachments/assets/7187bfd4-de1b-4640-8ac0-e1f535470032" width="65" height="65" > | <img src="https://github.com/user-attachments/assets/aeab1f38-d058-4de3-9fef-a0c1dd414637" width="65" height="65" > | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/89031782-58f1-4fdf-88a4-cec6cef029fa" alt="icon" width="65" height="55" /></div>

### Data
| MongoDB | Redis | Spring Data |
| :---: | :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/4821fb33-7da7-497b-9cfc-0ffc0ba436e2" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/c1e4b2e0-c032-4f53-b356-67a197d200b2" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://spring.io/img/projects/spring-data.svg" alt="icon" width="65" height="65" /></div>

### External API
| Gmail API | Google<br>OAuth API | Slack API |
| :---: | :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/79265390-c068-4ddc-bd84-a864bc10ef36" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/e6959bab-6d9b-44bd-bbc1-470ef7502447" alt="icon" width="65" height="65" /></div> | <img src="https://github.com/user-attachments/assets/8dc92556-f116-4829-8c27-8fd6e257e8c9" width="65" height="65" >

### Infra
| Kubernetes | Docker | ArgoCD | GitHub Actions |
| :---: | :---: | :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/a7dfdba3-f40e-48e6-83a3-da11bca758b7" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/f4cb5839-a012-4757-8534-0b2acb891fd2" alt="icon" width="65" height="65" /></div> | <img src="https://github.com/user-attachments/assets/d13b1657-0511-4509-9e94-47d6764fbda4" width="65" height="65" > | <img src="https://github.com/user-attachments/assets/16cb73c0-8a95-43dc-b084-ad2489dc4a76" width="65" height="65" > 

### Test & Docs
| JUnit | Swagger |
| :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/2c54371b-e8d2-4b29-b73a-1a90e7e791cd" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/72f9ce71-1eab-47b8-bc4e-d9ac2b142091" alt="icon" width="65" height="65" /></div> 

## 2. 채택한 개발 기술과 브랜치 전략

### Hexagonal Architecture
> **Hexagonal Architecture**를 통해 도메인 로직과 외부 시스템 간의 의존성을 최소화하고, 테스트 용이성을 높였습니다. 어댑터 계층을 통해 외부 시스템과의 통신을 분리하여, 도메인 로직이 외부 시스템에 영향을 받지 않도록 설계하였습니다.
### Domain Driven Design
> **Domain Driven Design**을 적용하여 도메인 모델을 중심으로 설계를 진행하였습니다. 각 도메인에 대한 명확한 경계를 설정하고, 도메인 서비스로 비즈니스 로직을 구현하였습니다. 이를 통해 코드의 가독성과 유지보수성을 높였습니다.
### Spring Cloud OpenFeign
> **Spring Cloud OpenFeign**을 사용하여 외부 API 호출을 간편하게 처리하였습니다. 이를 통해 REST API 호출을 인터페이스로 정의하고, 구현체를 자동으로 생성하여 코드의 일관성을 유지하였습니다.
### 브랜치 전략
- 브랜치 명: **Jira 태스크 ID 기반** ex) `CM-123`
- **Rebase Merge** 방식으로 main 브랜치에 병합
- **최소 1명 이상의 approve** 필요

## 3. 프로젝트 구조

```bash
src/main/java/com.starmix.checkmate
├── adapter                          # 외부 시스템과의 입출력을 담당하는 어댑터 계층
│   ├── in                           # 외부에서 들어오는 요청 처리 (API)
│   │   ├── common                   # 공통적으로 사용되는 DTO
│   │   ├── rest                     # REST API Controller / DTO
│   │   └── sse                      # SSE Controller / DTO
│   └── out                          # 외부 시스템 호출 (예: DB, 외부 API)
│       ├── ai                       # AI 서버 연동
│       ├── mail                     # 이메일 발송 API 연동 (예: Gmail API)
│       ├── oauth                    # OAuth 인증 관련 외부 연동
│       ├── persistence              # 데이터베이스 영속성 구현체 (MongoDB)
│       ├── redis                    # Redis 연동 구현체
│       └── slack                    # Slack Webhook 또는 API 연동 구현체
├── application                      # 도메인 로직을 실행하는 UseCase 계층
│   ├── service                      # UseCase 서비스 구현체
│   └── port                         # 어댑터-애플리케이션 계층 연결 포트 정의
│       └── out                      # 어댑터에서 구현해야 할 인터페이스 정의
│           ├── ai                   # AI 서버 연동을 위한 포트
│           ├── mail                 # 메일 발송 포트
│           ├── oauth                # OAuth 인증 포트
│           ├── persistence          # 영속성 포트 (Repository)
│           ├── redis                # Redis 연동 포트
│           └── slack                # Slack 연동 포트
├── domain                           # 도메인 모델 및 도메인 서비스 계층
│   ├── comment                      # 댓글 도메인
│   ├── dailyScrum                   # 데일리 스크럼 관련 도메인
│   ├── epic                         # 에픽 도메인
│   ├── feature                      # 기능(feature) 도메인
│   ├── meeting                      # 회의 관련 도메인
│   ├── notification                 # 알림 도메인
│   ├── project                      # 프로젝트 도메인
│   ├── sprint                       # 스프린트 도메인
│   ├── task                         # 태스크 도메인
│   └── user                         # 사용자 도메인
├── global                           # 전역적으로 사용되는 설정/처리 계층
│   └── exception                    # 전역 예외 처리 및 예외 정의
└── infrastructure                   # 설정 및 보안 등 인프라 관련 구성
    ├── config                       # 애플리케이션 설정 클래스 (ex: Bean 설정, 메시지 설정 등)
    └── security                     # Spring Security 및 인증/인가 관련 설정
```

## 4. 테스트

> **JUnit**을 기반으로 Domain / Mapper 유닛 테스트를 수행
