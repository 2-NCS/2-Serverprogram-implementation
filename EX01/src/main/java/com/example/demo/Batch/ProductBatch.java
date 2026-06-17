package com.example.demo.Batch;

import com.example.demo.Domain.Common.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
 * ===== [NCS 4. 배치 프로그램 구현] =====
 * ※ 배치/스케줄러는 아직 수업에서 다루지 않은 영역이다. 아래 설명을 읽고 직접 작성해 본다.
 *
 * [배경지식]
 *  - 배치(Batch) 프로그램 : 사용자의 직접 요청 없이, 정해진 주기마다 자동으로 실행되어
 *    대량·반복 작업을 처리하는 프로그램이다. (여기서는 "품절 상품 점검"을 주기적으로 수행)
 *  - @EnableScheduling : 스케줄러 기능을 켜는 설정. (Config/BatchConfig.java 에 이미 적용되어 있음)
 *  - @Scheduled         : 이 애너테이션이 붙은 메서드를 일정 주기로 자동 실행한다.
 *      · fixedDelay        = 직전 실행이 "끝난 뒤" N(ms) 후 다시 실행
 *      · fixedDelayString  = "${batch.soldout.delay:60000}" → 설정값이 없으면 60000ms(60초)
 *  - @Transactional(readOnly = true) : 조회 전용 트랜잭션. DB 연동을 안전하게 수행한다(NCS 4.2).
 */
@Component
@Slf4j
public class ProductBatch {

    @Autowired
    private ProductRepository productRepository;

    /*
     * [제공] 스케줄러 진입점 — 60초마다 자동으로 호출된다. (수정할 필요 없음)
     *  동작 : checkSoldOut() 으로 품절 개수를 구해 로그로 남긴다.
     *  실행 확인 : 앱을 켜 두면 콘솔에 60초마다 아래와 같은 로그가 찍힌다.
     *             [Batch] 품절 점검 완료 - 품절 상품 수=N
     */
    @Scheduled(fixedDelayString = "${batch.soldout.delay:60000}")
    @Transactional(readOnly = true)
    public void scheduleSoldOutCheck() {
        int count = checkSoldOut();
        log.info("[Batch] 품절 점검 완료 - 품절 상품 수=" + count);
    }

    /*
     * TODO: ★학생 작업★ 품절(stock = 0) 상품의 "건수"를 조회하여 int 로 반환하라.
     *
     *  [해야 할 일 — 단계별]
     *   1) 이 메서드 위에 @Transactional(readOnly = true) 애너테이션을 붙인다.
     *      → 조회 전용 트랜잭션 안에서 DB 에 안전하게 접근하기 위함.
     *   2) ProductRepository 에 품절 건수 조회용 쿼리메서드를 선언한다.
     *      → 예: long countByStock(int stock)   (Repository 파일의 TODO 참고, 선언만 하면 자동 구현)
     *   3) 이 본문에서 productRepository.countByStock(0) 을 호출하고,
     *      그 결과(long)를 int 로 변환하여 return 한다.
     *
     *  [동작 흐름]
     *   scheduleSoldOutCheck()(자동) → checkSoldOut()(여기) → countByStock(0) → 품절 건수 반환 → 로그 출력
     */
    public int checkSoldOut() {
        throw new UnsupportedOperationException("TODO: checkSoldOut 구현");
    }
}
