package com.sumx4ever.common;

/**
 * Created by xudongsun on 2017/12/28.
 */

public class Common {

    public interface Constance{
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8,9][0-9]{9}$";

        // 基础的网络请求地址
//        String API_URL = "http://192.168.1.103:8080/api/";
        String API_URL = "http://192.168.43.1:8080/api/";
    }
}
