package com.xdoj.demo.util;


import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.*;
import com.xdoj.demo.dto.AccessTokenDTO;
import com.xdoj.demo.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try  {
                Response response = client.newCall(request).execute();
                String[] str = response.body().string().split("=|&");
                return str[1];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public GithubUser getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
            try  {
                Response response = client.newCall(request).execute();
                String str = response.body().string();
                GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
