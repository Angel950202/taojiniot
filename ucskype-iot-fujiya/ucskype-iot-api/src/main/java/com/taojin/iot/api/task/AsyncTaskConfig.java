/*package com.taojin.iot.api.task;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncTaskConfig {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数量
        executor.setCorePoolSize(2);
        // 每个线程的最长存在时间
        executor.setKeepAliveSeconds(1800);
        // 最大的线程数量
        executor.setMaxPoolSize(20);
        // 缓冲队列
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }
}*/