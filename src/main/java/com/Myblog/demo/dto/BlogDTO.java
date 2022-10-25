package com.Myblog.demo.dto;

import com.Myblog.demo.domain.User;
import lombok.Data;

@Data
public class BlogDTO {
    private int id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
