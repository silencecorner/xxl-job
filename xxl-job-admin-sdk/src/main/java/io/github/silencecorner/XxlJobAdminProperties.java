package io.github.silencecorner;

import java.time.Duration;

public class XxlJobAdminProperties {
    /**
     * admin权限用户名
     */
    private String username;

    /**
     * admin权限用户密码
     */
    private String password;

    /**
     * cookie过期时间
     */
    private Duration cookieExpireAfter;


    /**
     * xxl-job admin address list：调度中心部署跟地址：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行 `执行器心跳注册` 和 `任务结果回调`.
     */
    private String addresses;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Duration getCookieExpireAfter() {
        return cookieExpireAfter;
    }

    public void setCookieExpireAfter(Duration cookieExpireAfter) {
        this.cookieExpireAfter = cookieExpireAfter;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
