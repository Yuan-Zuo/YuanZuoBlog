package com.xdoj.demo.dao;

import com.xdoj.demo.domain.QuestionPracticeDetail;
import com.xdoj.demo.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionPracticeDetailDao {
    //列出这个人做过的题
    @Select("select * from question_practice_detail where user_id = #{id}")
    public List<QuestionPracticeDetail> getByUserId(@Param("id") long id);

    @Insert("insert into question_practice_detail(user_id, question_id, result)values(#{userId},#{questionId},#{result}")
    public void insert(QuestionPracticeDetail questionPracticeDetail);

    @Delete("delete from question_practice_detail where user_id = #{userId} and question_id = #{questionId}")
    public void deleteByUserId(QuestionPracticeDetail questionPracticeDetail);
}
