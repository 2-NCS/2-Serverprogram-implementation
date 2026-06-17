package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "잘못된 비즈니스 요청입니다.")
public class MyBizException extends RuntimeException {
    public MyBizException(String message) {
        super(message);
    }
}
