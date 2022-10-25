package com.Myblog.demo.controller;

import com.Myblog.demo.dto.BlogDTO;
import com.Myblog.demo.dto.CommentDTO;
import com.Myblog.demo.enums.CommentTypeEnum;
import com.Myblog.demo.service.BlogService;
import com.Myblog.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/yuanzuoBlog")
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;

    @GetMapping("/blogs/{id}")
    public String blog(@PathVariable(name = "id") Integer id,
                       Model model){
        BlogDTO blogDTO = blogService.getBlogDTOById(id);
        List<BlogDTO> relatedBlogs = blogService.selectRelatedBlogs(blogDTO);
        List<CommentDTO> commentDTOS = commentService.getCommentsByParentIdAndType(id, CommentTypeEnum.BLOG);
        blogService.incView(id);
        model.addAttribute("blogDTO", blogDTO);
        model.addAttribute("relatedBlogs", relatedBlogs);
        model.addAttribute("comments", commentDTOS);
        return "blog";

    }
}
