# EX01 체크리스트 (제출 전 자가 점검)

## 개발환경 (NCS 1)
- [ ] build.gradle 에 web / data-jpa / validation / lombok / mysql / devtools 의존성을 구성했다
- [ ] application.properties + application-dev.properties 로 프로파일을 분리했다
- [ ] db/schema.sql 을 MySQL testdb 에 실행했다 (테이블 + seed 3건)
- [ ] gradlew bootRun 으로 8090 포트 기동된다
- [ ] Git 에 역할별 커밋·머지 이력을 남겼다
- [ ] 산출물: 개발환경_명세서 / 형상관리_방침 작성

## 공통모듈 (NCS 2)
- [ ] Controller 가 Repository 를 직접 호출하지 않고 Service 를 통한다
- [ ] 응답을 ResponseEntity<Map<String,Object>> 로 일관되게 래핑했다
- [ ] @RestControllerAdvice 전역 예외 처리를 적용했다
- [ ] ProductServiceTest 단위테스트(정상/롤백)가 통과한다
- [ ] 산출물: 테스트케이스_명세서 작성

## 서버프로그램 (NCS 3)
- [ ] 산출물: 업무프로세스맵 작성
- [ ] CRUD 5종(목록/단건/등록/수정/삭제)이 동작한다
- [ ] 등록/수정에 @Valid + BindingResult 로 필드 오류 시 400 + 필드 메시지를 반환한다
- [ ] register/modify 에 @Transactional 을 적용했다
- [ ] 보안: 음수 가격·재고를 @Valid 로 차단했고, 예외 응답에 내부정보를 노출하지 않는다

## 일괄처리 + 배치 (NCS 4)
- [ ] POST /api/products/bulk 가 List 를 한 트랜잭션으로 등록한다
- [ ] 중간에 한 건이라도 실패하면 전체가 롤백되어 0건 저장된다
- [ ] BatchConfig(@EnableScheduling) + ProductBatch(@Scheduled)로 품절 점검 배치가 동작한다
- [ ] ProductBatchTest 배치 테스트가 통과한다

## NCS 능력단위요소 매핑
| 요소 | 이 과제에서 |
|:--|:--|
| 1. 개발환경 구축 | Gradle 의존성 / 프로파일 / Git / 환경·형상관리 명세 |
| 2. 공통모듈 구현 | Service 분리 / 공통 응답·예외 / 단위테스트·명세 |
| 3. 서버프로그램 구현 | 프로세스맵 / REST CRUD / @Transactional / @Valid / 보안 |
| 4. 배치프로그램 구현 | 일괄(bulk) 트랜잭션 + @Scheduled 품절 점검 배치 + 테스트 |
