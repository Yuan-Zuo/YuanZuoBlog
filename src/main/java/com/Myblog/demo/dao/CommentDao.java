package com.Myblog.demo.dao;

import com.Myblog.demo.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao {

    @Insert("insert into comment (parent_id, type, commentator, gmt_creat, gmt_modify, like_count, content)values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModify},#{likeCount},#{content})")
    public void insert(Comment comment);

    @Select("select * from comment where id = #{parentId}")
    Comment selectByPrimaryKey(@Param("parentId") Integer parentId);

    @Select("select * from comment where parent_id = #{id} and type = #{type}")
    List<Comment> getCommentsByBlogId(@Param("id") Integer id, @Param("type") Integer type);

    @Update("update comment set comment_count = comment_count + 1 where id = #{parentId}")
    void incCommentCount(@Param("parentId") Integer parentId);
}
