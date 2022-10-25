package com.Myblog.demo.domain;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
