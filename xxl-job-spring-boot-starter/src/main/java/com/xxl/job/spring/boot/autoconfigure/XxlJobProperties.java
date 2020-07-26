package com.xxl.job.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangyanju
 * @version 1.0
 * @date 2019-04-11
 */
@ConfigurationProperties("xxl.job")
public class XxlJobProperties {
    /**
     * Enable xxl job
     */
    private Boolean enabled = false;
    /**
     * xxl-job, access token：执行器通讯TOKEN
     */
    private String accessToken;
    private AdminProperties admin;
    private ExecutorProperties executor;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        if (enabled != null) {
            this.enabled = enabled;
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        if (accessToken != null && accessToken.trim().length() > 0) {
            this.accessToken = accessToken;
        }
    }

    public AdminProperties getAdmin() {
        return admin;
    }

    public void setAdmin(AdminProperties admin) {
        this.admin = admin;
    }

    public ExecutorProperties getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorProperties executor) {
        this.executor = executor;
    }

    /**
     * AdminProperties
     */
    public static class AdminProperties {
        /**
         * xxl-job admin address list：调度中心部署跟地址：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行 `执行器心跳注册` 和 `任务结果回调`.
         */
        private String addresses;

        public String getAddresses() {
            return addresses;
        }

        public void setAddresses(String addresses) {
            this.addresses = addresses;
        }
    }

    /**
     * ExecutorProperties
     */
    public static class ExecutorProperties {

        private static final int MAX_PORT = 65535;

        /**
         * xxl-job executor address：执行器 `AppName` 和地址信息配置：AppName执行器心跳注册分组依据；地址信息用于 `调度中心请求并触发任务` 和 `执行器注册`。执行器默认端口为9999，执行器IP默认为空表示自动获取IP，多网卡时可手动设置指定IP，手动设置IP时将会绑定Host。单机部署多个执行器时，注意要配置不同执行器端口.
         */
        private String appName;
        /**
         * xxl-job log path：执行器运行日志文件存储的磁盘位置，需要对该路径拥有读写权限.
         */
        private String logPath;
        /**
         * 执行器ip
         */
        private String ip;
        /**
         * 执行器端口
         */
        private Integer port = 9999;
        /**
         * 日志保留天数 -1 不保留
         */
        private Integer logRetentionDays = -1;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getLogPath() {
            return logPath;
        }

        public void setLogPath(String logPath) {
            this.logPath = logPath;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            if (ip != null && ip.trim().length() > 0) {
                this.ip = ip;
            }
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            if (port != null && port > 0 && port <= MAX_PORT) {
                this.port = port;
            }
        }

        public Integer getLogRetentionDays() {
            return logRetentionDays;
        }

        public void setLogRetentionDays(Integer logRetentionDays) {
            if (logRetentionDays != null && logRetentionDays >= 0) {
                this.logRetentionDays = logRetentionDays;
            }
        }
    }
}
