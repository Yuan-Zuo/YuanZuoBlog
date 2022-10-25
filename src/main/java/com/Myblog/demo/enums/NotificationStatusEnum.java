package com.Myblog.demo.enums;

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    ;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }
}
