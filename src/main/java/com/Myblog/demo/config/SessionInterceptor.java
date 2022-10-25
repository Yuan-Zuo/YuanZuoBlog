package com.Myblog.demo.config;

import com.Myblog.demo.domain.User;
import com.Myblog.demo.service.NotificationService;
import com.Myblog.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user;
        if((user = userService.getUserViaCookie(request,response)) != null){
            Long unreadCount = notificationService.unreadCount(user.getId());
            request.getSession().setAttribute(UserService.SESSION_NAME_TOKEN, user);
            request.getSession().setAttribute("unReadCount",unreadCount);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
