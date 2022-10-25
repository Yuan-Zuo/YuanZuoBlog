package com.Myblog.demo.dto;

import com.Myblog.demo.domain.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Integer outerId;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
