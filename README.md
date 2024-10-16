# 일정 관리 애플리케이션

## 개요
이 애플리케이션은 사용자가 일정을 생성, 수정, 삭제, 그리고 배정할 수 있는 일정 관리 시스템입니다. 또한 사용자가 일정에 댓글을 추가할 수 있는 기능도 포함되어 있습니다. 사용자, 일정, 댓글 간의 관계가 명확하게 관리되며, 데이터를 상호작용할 수 있는 RESTful API를 제공합니다.

## 기능
- **사용자 관리**: 사용자 생성, 조회, 수정 및 삭제.
- **일정 관리**: 사용자가 일정을 생성, 조회, 수정 및 삭제할 수 있습니다. 각 일정은 사용자와 연관됩니다.
- **댓글 관리**: 일정에 댓글을 추가하고 CRUD 작업으로 관리할 수 있습니다.
- **일정 담당자 배정**: 사용자들을 일정의 담당자로 배정할 수 있습니다.
- **다대다 관계 관리**: 일정과 사용자 간의 다대다 관계를 schedule_users 테이블을 통해 관리합니다.
- **지연 로딩(Lazy Loading)**: 데이터베이스 조회 성능을 최적화하기 위해 지연 로딩 전략을 사용합니다.

## 사용 기술
- **Java 17**: 최신 언어 기능과 안정성을 위해 Java 17을 사용하여 애플리케이션을 개발했습니다.
- **Spring Boot**: RESTful 웹 서비스를 구축하고 의존성을 관리하기 위해 사용했습니다.
- **JPA (Java Persistence API)**: 엔터티를 사용하여 데이터베이스와 상호작용하기 위해 사용했습니다.

## ERD (엔터티 관계 다이어그램)
<img width="259" alt="ERD" src="https://github.com/user-attachments/assets/58af9508-1322-4f10-af8c-ea7cdfe446e5">

이 프로젝트의 ERD는 네 가지 주요 엔터티로 구성되어 있습니다:
- **User**: 시스템의 사용자를 나타냅니다. 각 사용자는 여러 개의 일정을 생성할 수 있습니다.
- **Schedule**: 사용자가 생성한 일정을 나타냅니다. 각 일정은 연관된 댓글을 가집니다.
- **Comment**: 일정에 작성된 댓글을 나타냅니다.
- **schedule_users**: 일정과 사용자 간의 다대다 관계를 관리하는 조인 테이블입니다.

### ERD 상세 설명
- **User와 Schedule의 관계**: 한 명의 사용자는 여러 개의 일정을 생성할 수 있으며, 각 일정은 하나의 사용자에 연관됩니다. (1:N 관계)
- **Schedule과 Comment의 관계**: 하나의 일정에는 여러 개의 댓글이 있을 수 있으며, 각 댓글은 하나의 일정에 연관됩니다. (1:N 관계)
- **User와 Schedule의 N대M 관계**: 일정 담당자 배정을 위해 User와 Schedule 간의 N대M 관계를 schedule_users 테이블로 관리합니다.

### API 엔드포인트
#### 사용자 엔드포인트
- **사용자 생성**: `POST /users`
  - **설명**: 새로운 사용자를 생성합니다.
  - **요청 본문**: 
    ```json
    {
      "name": "홍길동",
      "email": "hong@example.com"
    }
    ```
  - **응답**: 생성된 사용자의 ID와 성공 메시지.

- **ID로 사용자 조회**: `GET /users/{id}`
  - **설명**: 특정 사용자를 ID를 통해 조회합니다.
  - **요청 파라미터**: `id` - 조회할 사용자의 고유 식별자.
  - **응답**: 사용자 정보 (ID, 이름, 이메일 등).

- **모든 사용자 조회**: `GET /users`
  - **설명**: 모든 사용자를 조회합니다.
  - **응답**: 사용자 목록.

- **사용자 수정**: `PUT /users`
  - **설명**: 기존 사용자의 정보를 수정합니다.
  - **요청 본문**: 
    ```json
    {
      "id": 1,
      "name": "김철수",
      "email": "kim@example.com"
    }
    ```
  - **응답**: 수정된 사용자 정보와 성공 메시지.

- **사용자 삭제**: `DELETE /users/{id}`
  - **설명**: 특정 사용자를 삭제합니다.
  - **요청 파라미터**: `id` - 삭제할 사용자의 고유 식별자.
  - **응답**: 삭제 성공 메시지.

#### 일정 엔드포인트
- **일정 생성**: `POST /schedules`
  - **설명**: 새로운 일정을 생성합니다.
  - **요청 본문**:
    ```json
    {
      "userId": 1,
      "title": "회의",
      "content": "주간 팀 회의"
    }
    ```
  - **응답**: 생성된 일정의 ID와 성공 메시지.

- **ID로 일정 조회**: `GET /schedules/{id}`
  - **설명**: 특정 일정을 ID를 통해 조회합니다.
  - **요청 파라미터**: `id` - 조회할 일정의 고유 식별자.
  - **응답**: 일정 정보 (ID, 제목, 내용 등).

- **모든 일정 조회**: `GET /schedules`
  - **설명**: 모든 일정을 조회합니다.
  - **요청 파라미터**: 페이지 번호 (`page`), 페이지 크기 (`size`)를 쿼리 파라미터로 제공하여 페이지네이션 가능.
  - **응답**: 일정 목록.

- **일정 수정**: `PUT /schedules`
  - **설명**: 기존 일정을 수정합니다.
  - **요청 본문**:
    ```json
    {
      "id": 1,
      "title": "수정된 회의",
      "content": "주간 회의 수정 내용"
    }
    ```
  - **응답**: 수정된 일정 정보와 성공 메시지.

- **일정 삭제**: `DELETE /schedules/{id}`
  - **설명**: 특정 일정을 삭제합니다.
  - **요청 파라미터**: `id` - 삭제할 일정의 고유 식별자.
  - **응답**: 삭제 성공 메시지.

#### 댓글 엔드포인트
- **댓글 생성**: `POST /comments`
  - **설명**: 새로운 댓글을 생성합니다.
  - **요청 본문**:
    ```json
    {
      "scheduleId": 1,
      "content": "좋은 회의였습니다."
    }
    ```
  - **응답**: 생성된 댓글의 ID와 성공 메시지.

- **ID로 댓글 조회**: `GET /comments/{id}`
  - **설명**: 특정 댓글을 ID를 통해 조회합니다.
  - **요청 파라미터**: `id` - 조회할 댓글의 고유 식별자.
  - **응답**: 댓글 정보 (ID, 내용 등).

- **모든 댓글 조회**: `GET /comments`
  - **설명**: 모든 댓글을 조회합니다.
  - **응답**: 댓글 목록.

- **댓글 수정**: `PUT /comments`
  - **설명**: 기존 댓글을 수정합니다.
  - **요청 본문**:
    ```json
    {
      "id": 1,
      "content": "수정된 댓글 내용"
    }
    ```
  - **응답**: 수정된 댓글 정보와 성공 메시지.

- **댓글 삭제**: `DELETE /comments/{id}`
  - **설명**: 특정 댓글을 삭제합니다.
  - **요청 파라미터**: `id` - 삭제할 댓글의 고유 식별자.
  - **응답**: 삭제 성공 메시지.

## 참고 사항
- 이 애플리케이션은 기본 Spring Security 구성을 사용하며, 테스트를 위해 비활성화 클래스를 넣어놨습니다.(SecurityConfig.java)
- `@Transactional` 애너테이션은 데이터베이스 작업 중 적절한 트랜잭션 관리를 보장합니다.
- **페이징**: 모든 일정 조회 시 페이지네이션을 적용하여 대량의 데이터를 효율적으로 처리할 수 있습니다. 기본 페이지 크기는 10으로 설정되어 있습니다.
