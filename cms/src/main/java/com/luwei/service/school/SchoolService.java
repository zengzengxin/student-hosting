package com.luwei.service.school;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.model.school.pojo.cms.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-13
 */
@Service
@Slf4j
public class SchoolService extends ServiceImpl<SchoolMapper, School> {

    public SchoolVO findById(Integer schoolId) {
        School school = getById(schoolId);
        //TODO记得修改MessageCodes
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        return toSchoolVO(school);
    }

    private SchoolVO toSchoolVO(School school) {
        SchoolVO schoolVO = new SchoolVO();
        BeanUtils.copyNonNullProperties(school, schoolVO);
        return schoolVO;
    }

/*    @Transactional
    public SchoolVO saveSchool(SchoolAddDTO schoolAddDTO) {
        School school = new School();
        BeanUtils.copyNonNullProperties(schoolAddDTO, school);
        LocalDateTime time = LocalDateTime.now();
        school.setUpdateTime(time);
        school.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(school);
        Assert.isTrue(isSuccess, MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据---:{}", school);
        return toSchoolVO(school);
    }*/

    @Transactional
    public void deleteSchools(Set<Integer> schoolIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(schoolIds);
        Assert.isTrue(count == schoolIds.size(), MessageCodes.SCHOOL_DELETE_ERROR);
        log.info("删除数据:ids{}", schoolIds);
    }

/*    @Transactional
    public SchoolVO updateSchool(SchoolUpdateDTO schoolUpdateDTO) {
        School school = new School();
        BeanUtils.copyNonNullProperties(schoolUpdateDTO, school);

        school.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(school), MessageCodes.DATA_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", schoolUpdateDTO);
        return findById(school.getSchoolId());
    }*/

    public IPage<SchoolVO> findSchoolPage(SchoolQueryDTO schoolQueryDTO, Page page) {
        return baseMapper.getSchoolPage(page,schoolQueryDTO);
    }
}
