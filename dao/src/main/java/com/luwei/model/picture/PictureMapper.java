package com.luwei.model.picture;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
public interface PictureMapper extends BaseMapper<Picture> {

    int deleteByPictureTypeAndForeignKeyId(@Param("pictureType" ) Integer pictureType,
                                           @Param("foreignKeyId" ) Integer foreignKeyId);

    List<String> findAllByForeignKeyId(@Param("foreignKeyId" ) Integer foreignKeyId, @Param("pictureType" ) Integer pictureType);

}
