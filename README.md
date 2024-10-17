# 일정 관리 애플리케이션

## 개요
이 프로젝트는 Spring Boot와 JPA를 사용하여 개발된 일정 관리 시스템입니다. 사용자가 일정을 생성, 관리, 업데이트하고 댓글과 사용자 정보를 효과적으로 처리할 수 있도록 합니다. 주요 기능으로는 일정 관리, 사용자 배정, 댓글 처리, 일정 수정 등이 포함됩니다.

## 기능

### 1. 사용자 관리
- 사용자 정보 생성, 업데이트, 삭제 및 조회
- 각 사용자 정보는 `이름`, `이메일`, `생성일`, `수정일` 필드를 포함합니다.

### 2. 일정 관리
- 일정 생성, 업데이트, 삭제 및 조회
- 사용자에게 일정을 배정하거나 제거
- 일정의 `제목`, `내용`, `상태` 등의 세부 정보 수정
- 일정 상태 관리 (예: 진행 중, 완료, 취소)
- 여러 사용자를 일정에 협업자로 배정

### 3. 댓글 관리
- 일정에 관련된 댓글 생성, 업데이트, 삭제 및 조회
- 댓글은 `내용`, `생성일`, `수정일` 등의 필드를 포함하며 특정 일정과 연결됩니다.
- 사용자는 자신이 참여 중인 일정과 관련된 댓글을 관리할 수 있습니다.

### 4. 권한 관리
- 일정 소유자 및 배정된 사용자는 일정의 내용을 수정하고, 사용자 추가/제거, 상태 변경, 댓글 관리를 할 수 있는 권한이 있습니다.

## 사용 기술
- **Java 17**: 최신 언어 기능과 안정성을 위해 Java 17을 사용하여 애플리케이션을 개발했습니다.
- **Spring Boot**: RESTful 웹 서비스를 구축하고 의존성을 관리하기 위해 사용했습니다.
- **JPA (Java Persistence API)**: 엔터티를 사용하여 데이터베이스와 상호작용하기 위해 사용했습니다.

## ERD (엔터티 관계 다이어그램)
<img width="259" alt="ERD" src="https://github.com/user-attachments/assets/58af9508-1322-4f10-af8c-ea7cdfe446e5">

이 프로젝트의 ERD는 네 가지 주요 Entity로 구성되어 있습니다:
- **User**: 시스템의 사용자를 나타냅니다. 각 사용자는 여러 개의 일정을 생성할 수 있습니다.
- **Schedule**: 사용자가 생성한 일정을 나타냅니다. 각 일정은 연관된 댓글을 가집니다.
- **Comment**: 일정에 작성된 댓글을 나타냅니다.
- **schedule_users**: 일정과 사용자 간의 N대M 관계를 관리하는 join 테이블입니다.

### ERD 상세 설명
- **User와 Schedule의 관계**: 한 명의 사용자는 여러 개의 일정을 생성할 수 있으며, 각 일정은 하나의 사용자에 연관됩니다. (1:N 관계)
- **Schedule과 Comment의 관계**: 하나의 일정에는 여러 개의 댓글이 있을 수 있으며, 각 댓글은 하나의 일정에 연관됩니다. (1:N 관계)
- **User와 Schedule의 N대M 관계**: 일정 담당자 배정을 위해 User와 Schedule 간의 N대M 관계를 schedule_users 테이블로 관리합니다.

## 엔드포인트

### 사용자 엔드포인트
- `POST /users` - 새로운 사용자 생성
- `GET /users/{id}` - ID로 사용자 정보 조회
- `GET /users` - 모든 사용자 조회
- `PUT /users/{id}` - 사용자 정보 업데이트
- `DELETE /users/{id}` - 사용자 삭제

### 일정 엔드포인트
- `POST /schedules` - 새로운 일정 생성
- `PUT /schedules/{id}/assign-user` - 사용자에게 일정 배정
- `PUT /schedules/{id}/remove-user` - 사용자 제거
- `PUT /schedules/{id}/update-content` - 일정의 제목 및 내용 업데이트
- `PUT /schedules/{id}/status` - 일정 상태 업데이트

### 댓글 엔드포인트
- `POST /comments` - 새로운 댓글 생성
- `GET /comments/{id}` - ID로 댓글 조회
- `GET /comments` - 모든 댓글 조회
- `PUT /comments/{id}` - 댓글 업데이트
- `DELETE /comments/{id}` - 댓글 삭제

## 예시 요청
- 새로운 사용자 생성:
  ```json
  POST /users
  {
    "name": "홍길동",
    "email": "hong.gildong@example.com"
  }
  ```
- 사용자에게 일정 배정:
  ```sh
  PUT /schedules/1/assign-user?userId=2
  ```
- 일정에 댓글 추가:
  ```json
  POST /comments
  {
    "scheduleId": 1,
    "content": "이것은 댓글입니다."
  }
  ```

## 참고 사항
- 이 애플리케이션은 기본 Spring Security 구성을 사용하며, 테스트를 위해 비활성화 클래스를 넣어놨습니다.(SecurityConfig.java)
- `@Transactional` 애너테이션은 데이터베이스 작업 중 적절한 트랜잭션 관리를 보장합니다.
- **페이징**: 모든 일정 조회 시 페이지네이션을 적용하여 대량의 데이터를 효율적으로 처리할 수 있습니다. 기본 페이지 크기는 10으로 설정되어 있습니다.
