package com.Myblog.demo.dto;

import com.Myblog.demo.domain.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
