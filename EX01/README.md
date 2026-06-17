# EX01 — 서버 프로그램 구현 (상품 관리 REST API : S-Product)

> 분류: 모듈시험 (조별 과제) / 예상 3시간 / 합격선 60점
> NCS 능력단위: 서버 프로그램 구현 (2001020211_23v6) — 요소 1~4 전부 반영
> 기술 스택: Spring Boot 3.x · JPA · @Transactional · REST API · @Valid · @Scheduled 배치 · Lombok · MySQL testdb · 포트 8090

---

## 시나리오
직전 과제 B-Member(회원 MVC)에 이어, 이번에는 **상품(Product)을 관리하는 REST API 서버**를 구현한다.
Controller → Service → Repository 로 책임을 나누고, 공통 응답·예외를 공통 모듈로 분리하며,
REST CRUD + 트랜잭션 일괄처리(bulk) + **품절 점검 배치(@Scheduled)** 까지 구현한다.

## 이 문제는 "배운 범위"만으로 풀 수 있게 설계되었다
| 배운 것 | 이 과제에서 쓰는 곳 |
|:--|:--|
| 09_ORM_JPA | Product Entity / ProductRepository |
| 10_TX_JPA | @Transactional 등록·수정·일괄처리·배치 롤백 |
| 11_RESTCONTROLLER | @RestController + ResponseEntity + @CrossOrigin |
| 04_DATA_VALIDATION | @Valid + BindingResult + @NotBlank/@Min |
| 05_EXCEPTION | @RestControllerAdvice + MyBizException |
| 02_LOMBOK_DI | Service 계층 DI |

> 배치는 Spring 기본 `@Scheduled`(추가 라이브러리 없음)만 사용한다. Spring Security/Batch/JWT 등은 **사용 금지**.

## 기능 일람
| 기능 | 메서드/경로 | 처리 |
|:--|:--|:--|
| 목록 | GET `/api/products` | 페이징 조회 |
| 단건 | GET `/api/products/{id}` | 단건 조회 |
| 등록 | POST `/api/products` | @Valid 검증 후 저장 |
| 수정 | PUT `/api/products/{id}` | @Valid 검증 후 수정 |
| 삭제 | DELETE `/api/products/{id}` | 삭제 |
| 일괄등록 | POST `/api/products/bulk` | List 를 한 트랜잭션으로 등록, 중간 실패 시 전체 롤백 |
| 품절 점검(배치) | `@Scheduled` 스케줄러 | 주기적으로 품절(stock=0) 상품 수 집계·로그 |

## 폴더 구조
```
EX01/
├─ README.md / CHECKLIST.md
├─ build.gradle / settings.gradle            (제공)
├─ db/schema.sql                             (제공)
├─ docs/
│  ├─ 설계과제.md / 채점기준_체크리스트.md / 가이드.md
│  └─ 산출물_양식/ (개발환경_명세서 / 형상관리_방침 / 업무프로세스맵 / 테스트케이스_명세서)
└─ src/
   ├─ main/java/com/example/demo/
   │  ├─ DemoApplication.java                (제공)
   │  ├─ Config/TxConfig.java                (제공)
   │  ├─ Config/BatchConfig.java             (제공: @EnableScheduling)
   │  ├─ Batch/ProductBatch.java             ← TODO (checkSoldOut 본문)
   │  ├─ Controller/
   │  │  ├─ ProductRestController.java       ← TODO (전 매핑 본문)
   │  │  └─ GlobalException/GlobalExceptionHandler.java  (제공)
   │  ├─ Domain/Common/
   │  │  ├─ Entity/Product.java              (제공)
   │  │  ├─ Repository/ProductRepository.java ← TODO (쿼리메서드 3개 선언)
   │  │  ├─ Service/ProductService.java      (제공: 인터페이스)
   │  │  ├─ Service/ProductServiceImpl.java  ← TODO (전 메서드 본문)
   │  │  └─ Dtos/ProductDTO.java             (제공: 속성·검증) ← TODO (toEntity/from)
   │  └─ Exception/MyBizException.java       (제공)
   └─ test/java/com/example/demo/
      ├─ ProductServiceTest.java             ← TODO (정상/롤백)
      └─ ProductBatchTest.java               ← TODO (배치 동작)
```
> DataSource·EntityManagerFactory·TransactionManager 는 Spring Boot 자동설정을 사용하고(`@Transactional` 기본),
> `@Scheduled` 용 BatchConfig 만 명시 빈으로 제공한다.

## 코드 과제 (TODO 요약)
| 파일 | 채울 부분 |
|:--|:--|
| `ProductDTO.java` | `toEntity()`, `from()` 변환 메서드 (속성·검증은 제공) |
| `ProductRepository.java` | `existsByName`, `existsByNameAndIdNot`, `countByStock` 선언 |
| `ProductServiceImpl.java` | 전 메서드 본문 (register/modify/remove/get/list/registerBulk) + @Transactional |
| `ProductRestController.java` | 전 매핑 본문 (list/get/add/update/delete/addBulk) + @Valid·응답 래핑 |
| `ProductBatch.java` | `checkSoldOut()` 본문 (상세 주석 참고, @Transactional readOnly) |
| `ProductServiceTest.java` / `ProductBatchTest.java` | 정상·롤백·배치 테스트 |

## 실행법
1. MySQL `testdb` 에 `db/schema.sql` 실행 (테이블 + seed 3건)
2. `gradlew bootRun` (포트 8090) — 기동 시 배치가 주기적으로 품절 점검 로그를 남김
3. Postman/브라우저로 `http://localhost:8090/api/products` 확인

## 배점 (100점 / 합격선 60)
| 파트 | 배점 |
|:--|--:|
| 1. 개발환경 구축 | 10 |
| 2. 공통모듈 구현 | 25 |
| 3. 서버프로그램(REST) | 40 |
| 4. 일괄처리 + 배치 | 25 |

상세는 `docs/설계과제.md`, 채점은 `docs/채점기준_체크리스트.md`, 막히면 `docs/가이드.md` 와 `EX01_답/` 참고.
산출물 문서는 `docs/산출물_양식/` 의 4종을 채워 제출한다.
