package com.Myblog.demo.service;

import com.Myblog.demo.dao.BlogDao;
import com.Myblog.demo.dao.CommentDao;
import com.Myblog.demo.dao.NotificationDao;
import com.Myblog.demo.dao.UserDao;
import com.Myblog.demo.domain.Blog;
import com.Myblog.demo.domain.Comment;
import com.Myblog.demo.domain.Notification;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.CommentCreateDTO;
import com.Myblog.demo.dto.CommentDTO;
import com.Myblog.demo.enums.CommentTypeEnum;
import com.Myblog.demo.enums.NotificationStatusEnum;
import com.Myblog.demo.enums.NotificationTypeEnum;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    BlogDao blogDao;
    @Autowired
    UserDao userDao;

    @Autowired
    NotificationDao notificationDao;

    @Transactional
    public void insert(CommentCreateDTO commentCreateDTO, User user) {
        if(StringUtils.isBlank(commentCreateDTO.getContent())){
            throw new GlobalException(CodeMsg.COMMENT_EMPTY);
        }
        if(commentCreateDTO.getParentId() == null || commentCreateDTO.getParentId() == 0){
            throw new GlobalException(CodeMsg.TARGET_PARAM_NOT_FOUND);
        }
        if(commentCreateDTO.getType() == null || !CommentTypeEnum.isExist(commentCreateDTO.getType())){
            throw new GlobalException(CodeMsg.TYPE_PARAM_WRONG);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);

        Notification notification = null;

        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论
            Comment dbComment = commentDao.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new GlobalException(CodeMsg.COMMENT_NOT_FOUND);
            }
            Blog blog = blogDao.getBlogById(dbComment.getParentId());
            if(blog == null){
                throw new GlobalException(CodeMsg.BLOG_NOT_FOUND);
            }
            notification = createNotification(comment, blog.getCreator(), NotificationTypeEnum.REPLY_COMMENT, user.getName(), blog.getTitle(), blog.getId());

            //创建通知
        }else{
            //一级评论
            Blog blog = blogDao.getBlogById(comment.getParentId());
            if(blog == null){
                throw new GlobalException(CodeMsg.BLOG_NOT_FOUND);
            }
            blogDao.incComment(blog.getId());
            //创建通知
            notification = createNotification(comment, blog.getCreator(), NotificationTypeEnum.REPLY_BLOG, user.getName(), blog.getTitle(), blog.getId());
        }
        commentDao.insert(comment);
        commentDao.incCommentCount(comment.getParentId());
        notificationDao.insert(notification);
    }

    private Notification createNotification(Comment comment, Integer receiver, NotificationTypeEnum type, String userName, String outTitle, Integer outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());

        notification.setOuterId(outerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setType(type.getType());
        notification.setReceiver(receiver);
        notification.setNotifierName(userName);
        notification.setOuterTitle(outTitle);
        return notification;
    }

    public List<CommentDTO> getCommentsByParentIdAndType(Integer id, CommentTypeEnum type) {
        //获取id博客的第一级评论
        List<Comment> comments = commentDao.getCommentsByBlogId(id, type.getType());
        if(comments.isEmpty())return new ArrayList<>();
        //获取去重的评论的userId
        Set<Integer> commentators =  comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        //找到数据库User
        List<User> users = new ArrayList<>();
        for(Integer userId : commentators){
            users.add(userDao.findById(userId));
        }
        //将User和对应的comment组合
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user->user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
