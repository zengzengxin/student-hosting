package com.luwei.common.util;

import java.text.DecimalFormat;

/**
 * <p> 项目常用工具类
 *
 * @author luwei
 **/
public class CommonUtil {

    private CommonUtil() {
    }


    /**
     * 分转元（保留两位有效数字）
     *
     * @param amount
     * @return
     */
    public static String centToYuan(String amount) {
        DecimalFormat df = new DecimalFormat("######0.00" );
        Double d = Double.parseDouble(amount) / 100;
        return df.format(d);
    }


}
