package com.luwei.common.util;

import java.util.Random;

public class RadomUtil {

    public static String getNumRandom(int length) {



        String val = "";

        Random random = new Random();



        //参数length，表示生成几位随机数

        for(int i = 0; i < length; i++) {

            val += String.valueOf(random.nextInt(10));

        }

        return val;

    }


}
