package com.luwei.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author: huanglp
 * Date: 2018-12-18
 */
public class OrderIdUtils {

    /**
     * 时间戳 + 三个随机数, 作为订单编号
     *
     * @return
     */
    public static Long getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result.append(random.nextInt(10));
        }
        return Long.valueOf(newDate + result);
    }

}
