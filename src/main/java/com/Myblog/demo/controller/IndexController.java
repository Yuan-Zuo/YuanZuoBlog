package com.Myblog.demo.controller;

import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.PaginationDTO;
import com.Myblog.demo.result.Result;
import com.Myblog.demo.service.BlogService;
import com.Myblog.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/yuanzuoBlog")
public class IndexController {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    public final static Integer size = 5;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @RequestMapping("/index")
    public String toIndex(Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination = blogService.getBlogDTOList(search, page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search", search);
        return "index";
    }

    @RequestMapping("/blog")
    public String toBlog(Model model){
        return "MyJavaGuideUseLink";
    }

    @RequestMapping("/InnoDB")
    public String toLogin(Model model){
        return "Mysql内幕";
    }

    @RequestMapping("/InnoDBQA")
    public String MysqlQA(Model model){
        return "InnoDB内幕问答";
    }

    @RequestMapping("/JUC")
    public String toJUC(Model model){
        return "java并发编程之美";
    }

    @RequestMapping("/JUCQA")
    public String toJUCQA(Model model){
        return "java并发编程之美Juc问答";
    }

    @RequestMapping("/JVM")
    public String toJVM(Model model){
        return "jvm";
    }

    @RequestMapping("/Linux")
    public String toLinux(Model model){
        return "Linux命令";
    }

    @RequestMapping("/HighPerformanceMysql")
    public String toMysql(Model model){
        return "高性能Mysql";
    }

    @RequestMapping("/JavaDataStructure")
    public String toJavaDataStructure(Model model){
        return "java数据结构";
    }

    @RequestMapping("/LinuxQA")
    public String toLinuxQA(Model model){
        return "Linux问答";
    }

    @RequestMapping("/DBCollection")
    public String toDBCollection(Model model){
        return "数据库面经收集";
    }

    @RequestMapping("/Puzzle")
    public String toPuzzle(Model model){
        return "智力题";
    }

    @RequestMapping("/OS")
    public String toOperatingSystem(Model model){
        return "操作系统";
    }

    @RequestMapping("/InterfaceDesign")
    public String toInterfaceDesign(Model model){
        return "接口和系统设计";
    }

    @RequestMapping("/getBlog")
    @ResponseBody
    public Result<String> getBlog(HttpServletResponse response, long id){
        String blogContext = userService.getBlogContent(id);
        return Result.success(blogContext);
    }



}
