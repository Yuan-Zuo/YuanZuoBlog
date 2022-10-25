package com.Myblog.demo.controller;

import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.AccessTokenDTO;
import com.Myblog.demo.dto.GithubUser;
import com.Myblog.demo.service.UserService;
import com.Myblog.demo.util.GithubProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String clientRedirect_uri;
    private static Logger log = LoggerFactory.getLogger(AuthorizeController.class);
    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirect_uri);
//        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 token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if(githubUser != null){
            //登录成功 写cookie session
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            user.setId(userService.getUserIdByAccountId(githubUser.getId()));
            userService.login(response, user);
        }
        return "redirect:/yuanzuoBlog/index";
    };
    @GetMapping("/signOut")
    public String signOut(HttpServletRequest request,
                          HttpServletResponse response){
        userService.signOut(request, response);
        return "redirect:/yuanzuoBlog/index";
    }
}
