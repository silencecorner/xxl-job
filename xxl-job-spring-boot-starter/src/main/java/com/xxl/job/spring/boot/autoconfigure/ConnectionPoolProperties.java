package com.xxl.job.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("okhttp3.pool")
public class ConnectionPoolProperties {
    /**
     * 连接默认过期时间
     */
    private Duration timeout = Duration.ofSeconds(5);
    /**
     * 连接池最大空闲数
     */
    private Integer maxIdle = 100;

    /**
     * 最大生存时间秒
     */
    private Duration keepAlive = Duration.ofMinutes(10);

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Duration getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Duration keepAlive) {
        this.keepAlive = keepAlive;
    }
}
