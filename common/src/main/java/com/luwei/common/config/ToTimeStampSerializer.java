package com.luwei.common.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author: david
 * @Description: localDateTime转换为时间戳，使用时在需转换属性上添加 @JSONField(serializeUsing = ToTimeStampSerializer.class)
 * @Date； 2018/11/24
 **/

public class ToTimeStampSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {

        SerializeWriter out = jsonSerializer.out;

        if(null == o) {
            out.writeNull();
            return;
        }

        if(type.equals(LocalDateTime.class)) {
            Long milliSecond = ((LocalDateTime)o).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            out.writeLong(milliSecond);
        }
    }
}
