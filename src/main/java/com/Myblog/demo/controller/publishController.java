package com.Myblog.demo.controller;

import com.Myblog.demo.cache.TagCache;
import com.Myblog.demo.domain.Blog;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.result.CodeMsg;
import com.Myblog.demo.result.Result;
import com.Myblog.demo.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/yuanzuoBlog")
public class publishController {
    private static Logger log = LoggerFactory.getLogger(publishController.class);

    @Autowired
    private BlogService blogService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id
                        , Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("content", blog.getContent());
        model.addAttribute("title", blog.getTitle());
        model.addAttribute("tag", blog.getTag());
        model.addAttribute("id", id);

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String toPublish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    @ResponseBody
    public Result<String> doPublish(@Valid Blog blog, User user, Model model) {
        model.addAttribute("tags", TagCache.get());
        String inValid = TagCache.filterInValid(blog.getTag());
        if(StringUtils.isNotBlank(inValid)){
            return Result.error(CodeMsg.TAGS_ERROR);
        }
        blogService.creatOrUpdateBlog(blog, user);
        return Result.success("发布成功");
    }
}
