package com.Myblog.demo.domain;

/*
 *
 *Author: 严浩
 *Date: 2022/6/14
 */
public class ResultDTO {
    private int code;
    private String message;

    public ResultDTO() {
    }

    public ResultDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
