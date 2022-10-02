package com.xdoj.demo.dao;

import com.xdoj.demo.domain.Question;
import com.xdoj.demo.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface QuestionDetailDao {
    @Select("select * from question_detail where question_id = #{id}")
    public User getByQuestionId(@Param("id") long id);

    @Insert("insert into question_detail(question_id, difficult_level, administrator_id)values(#{questionId},#{difficultLevel},#{administratorId}")
    public void insert(Question questionDetail);

    @Delete("delete from question_detail where question_id = #{id}")
    public void deleteByQuestionId(@Param("id") long id);
}
