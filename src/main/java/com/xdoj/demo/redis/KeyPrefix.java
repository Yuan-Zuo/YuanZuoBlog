package com.xdoj.demo.redis;

//接口 抽象类 实现类 //模板模式
public interface KeyPrefix {
    public int expireSecond();
    public String getPrefix();
}
