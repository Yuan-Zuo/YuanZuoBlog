package com.Myblog.demo.controller;

import com.Myblog.demo.dao.CommentDao;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.CommentCreateDTO;
import com.Myblog.demo.dto.CommentDTO;
import com.Myblog.demo.enums.CommentTypeEnum;
import com.Myblog.demo.result.Result;
import com.Myblog.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/yuanzuoBlog")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Result<String> post(@RequestBody CommentCreateDTO commentCreateDTO, User user){
        commentService.insert(commentCreateDTO, user);
        return Result.success("成功");
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public Result<List<CommentDTO>> get(@PathVariable(name = "id") Integer id){
        List<CommentDTO> commentDTOS = commentService.getCommentsByParentIdAndType(id, CommentTypeEnum.COMMENT);
        return Result.success(commentDTOS);
    }
}
