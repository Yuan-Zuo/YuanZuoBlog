package com.Myblog.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("\\d{11}");
    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src))return false;
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}
