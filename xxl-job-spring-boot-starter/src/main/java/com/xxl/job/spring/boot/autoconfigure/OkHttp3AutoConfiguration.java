package com.xxl.job.spring.boot.autoconfigure;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(ConnectionPoolProperties.class)
public class OkHttp3AutoConfiguration {
    @Autowired
    private ConnectionPoolProperties connectionPoolProperties;

    @Bean
    public OkHttpClient okHttpClient(ConnectionPool connectionPool) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectionPoolProperties.getTimeout());
        builder.readTimeout(connectionPoolProperties.getTimeout());
        builder.connectionPool(connectionPool);
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPool okHttp3ConnectionPool() {
        int maxIdleConnections = connectionPoolProperties.getMaxIdle();
        Duration keepAliveDuration = connectionPoolProperties.getKeepAlive();
        return new ConnectionPool(maxIdleConnections, keepAliveDuration.toNanos(), TimeUnit.NANOSECONDS);
    }

}
