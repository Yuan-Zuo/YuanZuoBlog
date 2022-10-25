package com.Myblog.demo.dao;

import com.Myblog.demo.domain.User;
import com.Myblog.demo.vo.LoginVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") long id);

    @Select("select blog_content from blog where id = #{id}")
    public String getBlogContentById(@Param("id") long id);

    @Insert("insert into user(account_id,name,gmt_create,gmt_modify, avatar_url)values(#{accountId}, #{name}, #{gmtCreate},#{gmtModify},#{avatarUrl})")
    public void insertUser(User user);

    @Select("select * from user where id = #{id}")
    public User findById(@Param("id")int id);

    @Delete("delete from uer where user_id = #{id}")
    public void deleteById(@Param("id") long id);

    @Insert("insert into user (user_id, password) values(#{mobile}, #{password})")
    public void registerUser( LoginVo loginVo);

    @Select("select id from user where account_id = #{accountId}")
    Integer findUserIdByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},gmt_create=#{gmtCreate},gmt_modify=#{gmtModify}, avatar_url=#{avatarUrl} where account_id = #{accountId}")
    void updateUser(User user);
}
