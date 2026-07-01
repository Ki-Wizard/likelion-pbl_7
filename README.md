# LIKELION Spring Boot CRUD Assignment

Spring Boot 3.x 기반 멤버 관리 REST API입니다. 데이터는 메모리 저장소에 보관되며 애플리케이션 재시작 시 초기화됩니다.

작성자 : 김민수

## 요구 환경

- JDK 17 이상
- Maven Wrapper 포함

## 실행

```bash
./mvnw spring-boot:run
```

Windows PowerShell에서는 다음 명령을 사용할 수 있습니다.

```powershell
.\mvnw.cmd spring-boot:run
```

Maven이 별도로 설치되어 있다면 다음 명령도 사용할 수 있습니다.

```bash
mvn spring-boot:run
```

## API

| Method | URI | 설명 |
| --- | --- | --- |
| POST | `/members/lions` | Lion 등록 |
| POST | `/members/staffs` | Staff 등록 |
| GET | `/members/{name}` | 이름으로 단일 멤버 조회 |
| GET | `/members` | 전체 멤버 조회 또는 `?name=` 검색 |
| PUT | `/members/lions/{name}` | Lion 정보 수정 |
| PUT | `/members/staffs/{name}` | Staff 정보 수정 |
| DELETE | `/members/{name}` | 멤버 삭제 |

Swagger UI는 `http://localhost:8080/swagger-ui.html`에서 확인할 수 있습니다.
