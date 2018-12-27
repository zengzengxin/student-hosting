package com.luwei.service.picture;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.picture.Picture;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.picture.envm.PictureTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zzx
 * @since 2018-12-19
 */
@Service
@Slf4j
public class PictureService extends ServiceImpl<PictureMapper, Picture> {

    @Transactional(rollbackFor = Exception.class)
    public void savePicture(String url, Integer courseId, PictureTypeEnum pictureType) {
        Picture picture = new Picture();
        picture.setPictureUrl(url);
        // 图片类型为课程
        picture.setPictureType(pictureType);
        // 外键ID
        picture.setForeignKeyId(courseId);
        LocalDateTime time = LocalDateTime.now();
        picture.setUpdateTime(time);
        picture.setCreateTime(time);

        int count = baseMapper.insert(picture);
        Assert.isTrue(count > 0, MessageCodes.COURSE_SAVE_ERROR);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByPictureTypeAndForeignKeyId(Integer pictureType, Integer foreignKeyId) {
        return baseMapper.deleteByPictureTypeAndForeignKeyId(pictureType, foreignKeyId);
    }

    public List<String> findAllByForeignKeyId(Integer foreignKeyId, Integer pictureType) {
        return baseMapper.findAllByForeignKeyId(foreignKeyId, pictureType);
    }
}
