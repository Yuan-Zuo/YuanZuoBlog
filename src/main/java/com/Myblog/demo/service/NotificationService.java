package com.Myblog.demo.service;

import com.Myblog.demo.dao.NotificationDao;
import com.Myblog.demo.dao.UserDao;
import com.Myblog.demo.domain.Notification;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.NotificationDTO;
import com.Myblog.demo.dto.PaginationDTO;
import com.Myblog.demo.enums.NotificationStatusEnum;
import com.Myblog.demo.enums.NotificationTypeEnum;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationDao notificationDao;
    @Autowired
    UserDao userDao;

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        int offset;
        if(page > (offset = size * (page - 1)) + 1 || page <= 0){
            throw new GlobalException(CodeMsg.PARAMETER_ERROR);
        }
        Integer totalCount = getNotificationSize();
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Notification> notifications = getNotificationList(offset, size);
        if(notifications.isEmpty()){
            return paginationDTO;
        }


        List<NotificationDTO>  notificationDTOs = new ArrayList<>();
        for (Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOs.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOs);
        paginationDTO.setPagination(totalCount, page, size);
        return  paginationDTO;
    }

    private List<Notification> getNotificationList(int offset, Integer size) {
        return notificationDao.getNotificationList(offset, size);
    }

    private Integer getNotificationSize() {
        return notificationDao.getNotificationSize();
    }

    public Long unreadCount(Integer id) {
        return notificationDao.unreadCount(id);
    }

    public NotificationDTO read(Long notificationId, User user) {
        Notification notification = notificationDao.selectByKey(notificationId);
        if(notification == null){
            throw new GlobalException(CodeMsg.NOTIFICATION_NOT_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            throw new GlobalException(CodeMsg.READ_NOTIFICATION_FAIL);
        }
        notificationDao.read(notificationId, NotificationStatusEnum.READ.getStatus());
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
