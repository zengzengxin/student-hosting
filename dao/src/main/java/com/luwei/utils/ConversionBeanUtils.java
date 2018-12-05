package com.luwei.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Check;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: ffq
 * @Description:T->C转换工具类
 * @Date: Create in 19:38 2018/11/21
 */
public class ConversionBeanUtils {

    public static <T, C> IPage<C> conversionBean(IPage<T> page, Function<T, C> function) {
        List<C> collect = page.getRecords().stream().map(function).collect(Collectors.toList());
        IPage<C> resultPage = new Page();
        BeanUtils.copyProperties(page, resultPage);
        resultPage.setRecords(collect);
        return resultPage;
    }

}
