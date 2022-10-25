package com.xdoj.demo.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;
    private String prefix;

//    this( ) 用来访问本类的构造方法，括号中可以有参数，如果有参数就是调用指定的有参构造方法。
    public BasePrefix(String prefix){//默认0代表永不过期
        this(0,prefix);
    }
    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSecond() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
