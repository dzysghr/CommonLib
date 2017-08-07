package com.dzy.commemlib.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public final class StringUtils {
    private StringUtils(){}


    public static String urlEncode(String str){
        return urlEncode(str,str);
    }

    public static String urlEncode(String str,String defaultStr){
        String t;
        try {
            t = URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return defaultStr;
        }
        return t;
    }
}
