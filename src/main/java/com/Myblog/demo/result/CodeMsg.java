package com.Myblog.demo.result;

public class CodeMsg {


    private int code;
    private String msg;


    //
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    //通用异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "绑定异常:%s");
    //登录模块异常 5002xx
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500201, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500202, "手机号不能为空");
    public static CodeMsg REGISTER_ERROR = new CodeMsg(500203,"注册信息不能为空");
    public static CodeMsg REGISTER_FAILED = new CodeMsg(500204,"注册失败请重试");
    public static CodeMsg USER_EXIST_ERROR = new CodeMsg(500205,"用户已存在");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500206, "手机号错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500207,"用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500208, "密码错误");
    public static CodeMsg USER_NOT_SIGN_IN = new CodeMsg(500209,"请先登录");
    public static CodeMsg PARAMETER_ERROR = new CodeMsg(500210,"参数错误");


    //回复模块 5003xx
    public static CodeMsg TARGET_PARAM_NOT_FOUND = new CodeMsg(500301,"未选中评论或回复进行回复");
    public static CodeMsg TYPE_PARAM_WRONG = new CodeMsg(500302,"评论类型错误或不存在");
    public static final CodeMsg COMMENT_NOT_FOUND = new CodeMsg(500303,"回复的评论已不存在");
    public static final CodeMsg BLOG_NOT_FOUND = new CodeMsg(500303,"回复的博客已不存在");
    public static final CodeMsg COMMENT_EMPTY = new CodeMsg(500303,"评论不能为空");
    public static final CodeMsg READ_NOTIFICATION_FAIL = new CodeMsg(500304,"错误读取他人信息");
    public static final CodeMsg NOTIFICATION_NOT_FOUND = new CodeMsg(500304,"消息不存在");
    //发布模块
    public static final CodeMsg TAGS_ERROR = new CodeMsg(500403,"标签有误，请使用已有标签");
    //注册模块 5004xx

    //添加题目模块 5005xx
    public static CodeMsg QUESTION_EMPTY = new CodeMsg(500505, "题目不能为空");
    //

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
//    以下两个方法“等效”，且不能在一个类下同时定义
//    private static int sumUp(int... values) {
//    }
//    private static int sumUp(int[] values) {
//    }

    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code, message);
    }
    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
