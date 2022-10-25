package com.Myblog.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GithubUser {
    private String name;
    private int id;
    private long accountId;
    private String bio;
    private String avatarUrl;

}
