package com.example.demo.Controller.GlobalException;

import com.example.demo.Exception.MyBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 비즈니스 예외 → 400 (민감정보 미노출: 메시지만 전달)
    @ExceptionHandler(MyBizException.class)
    public ResponseEntity<Map<String, Object>> handleBiz(MyBizException e) {
        log.error("[Biz] " + e.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 그 외 예외 → 500 (스택트레이스/내부정보 노출 금지)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception e) {
        log.error("[Etc] " + e);
        Map<String, Object> body = new HashMap<>();
        body.put("error", "서버 처리 중 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
