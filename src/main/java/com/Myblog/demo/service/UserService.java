package com.Myblog.demo.service;

import com.Myblog.demo.controller.IndexController;
import com.Myblog.demo.dao.UserDao;
import com.Myblog.demo.dao.UserMapper;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.domain.UserExample;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.redis.RedisService;
import com.Myblog.demo.redis.UserKey;
import com.Myblog.demo.result.CodeMsg;
import com.Myblog.demo.util.UUIDUtil;
import com.Myblog.demo.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);
    public static final String COOKIE_NAME_TOKEN = "token";
    public static final String SESSION_NAME_TOKEN = "user";

    public static final String yes = "yes";
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    public String getBlogContent(long id) {
        return userDao.getBlogContentById(id);
    }

    private User getById(long id) {
        return userDao.getById(id);
    }

    private void registerUser(LoginVo loginVo) {
        userDao.registerUser(loginVo);
    }

    public void register(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.REGISTER_ERROR);
        }
        String mobile = loginVo.getMobile();
        User user = getById(Long.parseLong(mobile));
        if (user != null) {
            throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
        }
        registerUser(loginVo);
    }

    public boolean login(HttpServletResponse response, User user) {
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    //    public 方法第一步要进行参数校验
//    更新cookie时间 : 设置一个新cookie
    public User getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
//        延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public User getUserViaCookie(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken)) {
            return null;
        }
        User user = getUserByToken(response, cookieToken);
        return user;
    }

    public void deleteUserCookie(HttpServletRequest request) {
        String token = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);
        redisService.delete(UserKey.token, token);
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        //生成cookie
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置cookie 有效期 和 redis 过期时间一致
        cookie.setMaxAge(UserKey.token.expireSecond());
        //显示cookie生命周期
        //因此cookie.setPath("/");之后，可以在webapp文件夹下的所有应用共享cookie，
        //而cookie.setPath("/webapp_b/")是指设置的cookie只能在webapp_b应用下的获得，
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Transactional
    public void createOrUpdate(User user) {
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
//        List<User> user1 = userMapper.selectByExample(userExample);
        Integer user1 = userDao.findUserIdByAccountId(user.getAccountId());
        if (user1 == 0) {
            userDao.insertUser(user);
        } else {
            userDao.updateUser(user);
        }
    }

    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        deleteUserCookie(request);
        request.getSession().removeAttribute(UserService.SESSION_NAME_TOKEN);
        //删除浏览器cookie
        Cookie cookie = new Cookie(UserService.COOKIE_NAME_TOKEN, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Integer getUserIdByAccountId(int id) {
       return userDao.findUserIdByAccountId(String.valueOf(id));
    }
}
