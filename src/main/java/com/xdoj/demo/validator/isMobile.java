package com.xdoj.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;

@Documented
@Constraint(validatedBy = {isMobileValidator.class})
public @interface isMobile {

//    required=true表示前端必须传参数。
//    required=false表示前端不传参数的时候，会将参数置为null。因此假如参数是int这种不能赋值为null的类型，就可能会报错。
    boolean required() default true;



    String message() default "学号或管理员号错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
