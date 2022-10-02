package com.xdoj.demo.dto;

import org.springframework.stereotype.Component;

@Component
public class AccessTokenDTO {
    //从 GitHub 收到的 OAuth App 的客户端 ID。
    private String client_id;
    //从 GitHub 收到的 OAuth App 的客户端密码。
    private String client_secret;
    // 收到的作为对步骤 1 的响应的代码。
    private String code;
    //用户获得授权后被发送到的应用程序中的 URL。
    private String redirect_uri;
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
