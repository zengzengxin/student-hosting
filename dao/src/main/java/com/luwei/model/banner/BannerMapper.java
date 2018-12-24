package com.luwei.model.banner;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
public interface BannerMapper extends BaseMapper<Banner> {

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
        System.out.println();
        String timestamp = String.valueOf(new Date().getTime());
        System.out.println(timestamp);
        System.out.println();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        System.out.println(newDate);
    }
}
