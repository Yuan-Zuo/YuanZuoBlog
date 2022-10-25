package com.Myblog.demo.dao;

import com.Myblog.demo.domain.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogDao {
    @Insert("insert into blog(title,content,gmt_create,gmt_modify,creator,tag)values(#{title},#{content},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
    public void insertBlog(Blog blog);

    @Select("select * from blog order by gmt_modify desc limit #{offset}, #{size}")
    List<Blog> getBlogList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(*) from blog")
    public Integer getBlogSize();

    @Select("select * from blog where creator = #{id} limit #{offset}, #{size}")
    List<Blog> getBlogListById(@Param("id")Integer id, @Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select count(*) from blog where creator = #{id}")
    int getBlogSizeById(@Param("id")Integer id);
    @Select("select * from blog where id = #{id}")
    Blog getBlogById(@Param("id") Integer id);

    @Update("update blog set title = #{title}, content=#{content}, tag = #{tag} where id = #{id}")
    void updateBlog(Blog blog);

    @Update("update blog set view_count = view_count + 1 where id = #{id}")
    void incView(@Param("id") Integer id);

    @Update("update blog set comment_count = comment_count + 1 where id = #{id}")
    void incComment(Integer id);
    @Select("select * from blog where tag REGEXP #{tag} and id != #{id} limit 10")
    List<Blog> getRelatedBlogs(Blog blog);

    @Select("select * from blog where title REGEXP #{tags} limit #{offset}, #{size}")
    List<Blog> getSearchBlogList(@Param("tags") String tags, @Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select count(*) from blog where title REGEXP #{tags}")
    Integer getSearchBlogSize(@Param("tags") String tags);
}
