package com.xxl.job.spring.boot.autoconfigure;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import io.github.silencecorner.XxlJobAdminApi;
import io.github.silencecorner.XxlJobAdminProperties;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Objects;

/**
 * @author yangyanju
 * @version 1.0
 * @date 2019-04-11
 */
@Configuration
@ConditionalOnClass(XxlJobSpringExecutor.class)
@ConditionalOnProperty(prefix = "xxl.job", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({XxlJobProperties.class})
@Import(OkHttp3AutoConfiguration.class)
public class XxlJobAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);

    private final XxlJobProperties properties;

    public XxlJobAutoConfiguration(XxlJobProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public XxlJobExecutor xxlJobExecutor() {
        LOGGER.info(">>>>>>>>>>> xxl-job config init.");

        XxlJobAdminProperties admin = this.properties.getAdmin();
        XxlJobProperties.ExecutorProperties executor = this.properties.getExecutor();

        Objects.requireNonNull(admin, "xxl job admin properties must not be null.");
        Objects.requireNonNull(executor, "xxl job executor properties must not be null.");

        XxlJobExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        xxlJobExecutor.setIp(executor.getIp());
        xxlJobExecutor.setPort(executor.getPort());
        xxlJobExecutor.setAppname(executor.getAppName());
        xxlJobExecutor.setLogPath(executor.getLogPath());
        xxlJobExecutor.setLogRetentionDays(executor.getLogRetentionDays());
        xxlJobExecutor.setAdminAddresses(admin.getAddresses());
        xxlJobExecutor.setAccessToken(this.properties.getAccessToken());

        return xxlJobExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    public XxlJobAdminApi xxlJobAdminApi(OkHttpClient okHttpClient) {
        return new XxlJobAdminApi(properties.getAdmin(),properties.getExecutor().getAppName(), okHttpClient);
    }
}
