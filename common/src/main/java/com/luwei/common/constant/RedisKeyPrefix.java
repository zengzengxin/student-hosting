package com.luwei.common.constant;

/**
 * @author luwei
 */
public class RedisKeyPrefix {

    public static String captcha(String uuid) {
        return "captcha:" + uuid;
    }

    public static String index(int protoId) {
        return "index:" + protoId;
    }

}
