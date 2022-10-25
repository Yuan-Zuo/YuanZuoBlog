package com.Myblog.demo.service;

import com.Myblog.demo.controller.IndexController;
import com.Myblog.demo.dao.QuestionDao;
import com.Myblog.demo.domain.Question;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import com.Myblog.demo.vo.QuestionListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    QuestionDao questionDao;

    public void addQuestion(Question question){
        if(question == null)throw new GlobalException(CodeMsg.QUESTION_EMPTY);
        questionDao.addQuestion(question);
    }

    public Question getQuestion(long id){
        Question question = questionDao.getQuestion(id);
        return  question;
    }

    public List<QuestionListVo> getQuestionListVo() {
        List<QuestionListVo> questionListVo = questionDao.getQuestionListVo();
        return questionListVo;
    }
}
