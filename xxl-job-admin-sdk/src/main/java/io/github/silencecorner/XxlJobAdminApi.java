package io.github.silencecorner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xxl.job.core.biz.model.ReturnT;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XxlJobAdminApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(XxlJobAdminApi.class);

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private final Object object = new Object();
    private final XxlJobAdminProperties properties;
    private OkHttpClient okHttpClient;
    private LocalDateTime expireUntil;
    private String cookie;
    private String appName;
    private Integer jobGroupId;

    /**
     * gson默认不序列化null
     */
    private static final Gson GSON = new GsonBuilder().create();

    public XxlJobAdminApi(XxlJobAdminProperties properties,String appName, OkHttpClient okHttpClient) {
        this.properties = properties;
        this.okHttpClient = okHttpClient;
        this.expireUntil = LocalDateTime.now().plus(properties.getCookieExpireAfter());
        this.appName = appName;
        ReturnT<String> returnT = login();
        if (null == returnT && returnT.getCode() == 200) {
            LOGGER.info("重新登录获取cookie");
        }else{
            LOGGER.error("未能成功登录xxl-job-admin请检查");
        }
        this.jobGroupId = this.getRemoteJobGroupId();
    }

    public ReturnT<String> addJob(XxlJobInfo xxlJobInfo){
        Type type = new TypeToken<ReturnT<String>>(){}.getType();
        return this.postFormForObject(getUrl("/jobinfo/add"),xxlJobInfo,type);
    }

    public ReturnT<String> updateJob(XxlJobInfo xxlJobInfo){
        Type type = new TypeToken<ReturnT<String>>(){}.getType();
        return postFormForObject(getUrl("/jobinfo/update"),xxlJobInfo,type);
    }

    private String getUrl(String serviceName) {
        if (this.expireUntil.isBefore(LocalDateTime.now())) {
            if (this.expireUntil.isBefore(LocalDateTime.now())) {
                this.expireUntil = LocalDateTime.now().plus(properties.getCookieExpireAfter());
            }
            ReturnT<String> returnT = login();
            if (returnT == null) {
                LOGGER.info("重新登录获取cookie,返回结果code [{}] msg [{}] content [{}]", returnT.getCode(), returnT.getMsg(), returnT.getContent());
            }else{
                LOGGER.error("未能成功登录xxl-job-admin请检查");
            }

        }
        return properties.getAddresses() + serviceName;
    }


    private Integer getRemoteJobGroupId(){
        Map<String,String> param = new HashMap<>(1);
        param.put("appname",appName);
        Type type = new TypeToken<Map<String,Object>>(){}.getType();
        Map<String,Object> result = this.getForObject(getUrl("/jobgroup/pageList"),param,type);
        if (null != result && result.get("data") != null){
            Map<String,Object> first = ((ArrayList<Map<String,Object>>)result.get("data")).get(0);
            return Double.valueOf(first.get("id").toString()).intValue();
        }
        throw new RuntimeException(String.format("未找到[{}]的jobGroupId",appName));
    }

    private synchronized ReturnT<String> login(){
        Type type = new TypeToken<ReturnT<String>>(){}.getType();
        String url = properties.getAddresses() + "/login";
        Map<String,String> map = new HashMap<>(2);
        Request request = new Request.Builder()
                .post(new FormBody.Builder()
                        .addEncoded("userName",properties.getUsername())
                        .addEncoded("password",properties.getPassword())
                        .build())
                .url(url)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() == null) {
                return null;
            }
            cookie = response.header("Set-Cookie");
            return GSON.fromJson(new InputStreamReader(response.body().byteStream()), type);
        } catch (IOException e) {
            throw new UnexpectException(e);
        }
    }


    private <T> T postForObject(String url, Object req, Type type) {
        Request request = new Request.Builder()
                .post(RequestBody.create(JSON, GSON.toJson(req)))
                .header("Cookie", cookie)
                .url(url)
                .build();
        return request(request, type);
    }

    private <T> T postFormForObject(String url, Object req, Type type) {
        FormBody.Builder builder = new FormBody.Builder();
        if (req != null){
            final Map<String,String> map =  GSON.fromJson(GSON.toJson(req), new TypeToken<Map<String,String>>(){}.getType());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addEncoded(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .post(builder.build())
                .header("Cookie", cookie)
                .url(url)
                .build();
        return request(request,type);
    }

    private <T> T getForObject(final String url, final Object req,final Type type) {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        if (req instanceof Map) {
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) req).entrySet()) {
                builder.addQueryParameter(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        } else {
            if (req != null){
              final Map<String,String> map =  GSON.fromJson(GSON.toJson(req), new TypeToken<Map<String,String>>(){}.getType());
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .get()
                .header("Cookie", cookie)
                .url(builder.build())
                .build();
        return request(request, type);
    }

    private <T> T request(Request request, Type type) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() == null) {
                return null;
            }
            return GSON.fromJson(new InputStreamReader(response.body().byteStream()), type);
        } catch (IOException e) {
            throw new UnexpectException(e);
        }
    }

    public Integer getJobGroupId() {
        return jobGroupId;
    }
}
