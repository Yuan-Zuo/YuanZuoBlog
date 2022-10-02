package com.xdoj.demo.domain;

/*
 *
 *Author: 严浩
 *Date: 2022/6/14
 */

public class CodeDTO {
    private String code;
    private long questionId;

    public CodeDTO() {
    }

    public CodeDTO(String code, long questionId) {
        this.code = code;
        this.questionId = questionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
