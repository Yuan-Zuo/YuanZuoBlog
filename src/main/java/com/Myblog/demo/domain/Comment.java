package com.Myblog.demo.domain;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer likeCount;
    private Integer commentCount;
    private String content;
}
