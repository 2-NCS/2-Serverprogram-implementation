package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

// 배치(@Scheduled) 활성화 — Spring 기본 스케줄러 사용(추가 라이브러리 없음)
@Configuration
@EnableScheduling
public class BatchConfig {
}
