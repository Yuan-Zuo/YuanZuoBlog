package com.xdoj.demo.dao;

import com.xdoj.demo.domain.Question;
import com.xdoj.demo.vo.QuestionListVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionDao {

    @Select("select * from question where question_id = #{id}")
    public Question getQuestion(@Param("id")long id);

    @Insert("insert into question (question_content, difficult_level, question_name) values(#{questionContent}, #{difficultLevel}, #{questionName})")
    public void addQuestion(Question question);

    @Select("select question_id, question_name, difficult_level from question")
    List<QuestionListVo> getQuestionListVo();
}
