package com.luwei.model.user.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jdq
 * @date 2017/12/15 16:36
 */
@Data
@Accessors(chain = true)
public class WeChatUser {

    /**
     * openId
     */
    private String openId;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

}
