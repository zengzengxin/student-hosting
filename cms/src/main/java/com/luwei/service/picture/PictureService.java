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
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-19
 */
@Service
@Slf4j
public class PictureService extends ServiceImpl<PictureMapper, Picture> {
   /* public PictureVO findById(Integer pictureId) {
        Picture picture = getById(pictureId);
        //TODO记得修改MessageCodes
        Assert.notNull(picture, MessageCodes.DATA_IS_NOT_EXIST);
        return toPictureVO(picture);
    }

    private PictureVO toPictureVO(Picture picture) {
        PictureVO pictureVO = new PictureVO();
        BeanUtils.copyNonNullProperties(picture, pictureVO);
        return pictureVO;
    }

    @Transactional
    public PictureVO savePicture(PictureAddDTO pictureAddDTO) {
        Picture picture = new Picture();
        BeanUtils.copyNonNullProperties(pictureAddDTO, picture);
        LocalDateTime time = LocalDateTime.now();
        picture.setUpdateTime(time);
        picture.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(picture);
        Assert.isTrue(isSuccess, MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据---:{}", picture);
        return toPictureVO(picture);
    }

    @Transactional
    public void deletePictures(Set<Integer> pictureIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(pictureIds);
        Assert.isTrue(count == pictureIds.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据:ids{}", pictureIds);
    }

    @Transactional
    public PictureVO updatePicture(PictureUpdateDTO pictureUpdateDTO) {
        Picture picture = new Picture();
        BeanUtils.copyNonNullProperties(pictureUpdateDTO, picture);

        picture.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(picture), MessageCodes.DATA_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", pictureUpdateDTO);
        return findById(picture.getPictureId());
    }

    public IPage<PictureVO> findPicturePage(PictureQueryDTO pictureQueryDTO, Page page) {
        Picture picture = new Picture();
        BeanUtils.copyNonNullProperties(pictureQueryDTO, picture);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        //查询业务
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toPictureVO);
    }*/

    @Transactional
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

    @Transactional
    public int deleteByPictureTypeAndForeignKeyId(Integer pictureType, Integer foreignKeyId) {
        return baseMapper.deleteByPictureTypeAndForeignKeyId(pictureType, foreignKeyId);
    }

    public List<String> findAllByForeignKeyId(Integer foreignKeyId, Integer pictureType) {
        return baseMapper.findAllByForeignKeyId(foreignKeyId, pictureType);
    }
}
