package com.Myblog.demo.dao;

import com.Myblog.demo.domain.Notification;
import com.Myblog.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface NotificationDao {

    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,`status`,notifier_name,outer_title)values(#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle}) ")
    void insert(Notification notification);
    @Select("select count(*) from notification")
    Integer getNotificationSize();

    @Select("select * from notification order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> getNotificationList(@Param("offset") Integer offset, @Param("size")Integer size);
    @Select("select count(*) from notification where receiver = #{id} and `status` = 0")
    Long unreadCount(Integer id);

    @Select("select * from notification where id = #{id}")
    Notification selectByKey(@Param("id") Long id);

    @Update("update notification set `status` = #{status} where id = #{id}")
    void read(@Param("id") Long id, @Param("status") Integer status);
}
