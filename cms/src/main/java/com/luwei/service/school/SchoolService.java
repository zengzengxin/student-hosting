package com.luwei.service.school;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ReadExcelUtil;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import com.luwei.model.school.envm.SchoolTypeEnum;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.model.school.pojo.cms.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteSchools(Set<Integer> schoolIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(schoolIds);
        Assert.isTrue(count == schoolIds.size(), MessageCodes.SCHOOL_DELETE_ERROR);
        log.info("删除数据:ids{}", schoolIds);
    }

    /*@Transactional
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
        return baseMapper.getSchoolPage(page, schoolQueryDTO);
    }

    public List<SchoolVO> findSchoolPage() {
        return list(new QueryWrapper<>()).stream().map(this::toSchoolVO).collect(Collectors.toList());
        // QueryWrapper queryWrapper = new QueryWrapper();
        // return baseMapper.selectPage(page,queryWrapper);
    }



    public Boolean readExcelFile(MultipartFile file) {
        String result;

        //解析excel，获取上传的事件单
        List<Map<Integer, String>> mapList = ReadExcelUtil.getExcelInfo(file);
        Assert.notNull(mapList, MessageCodes.EXCEL_HAS_NO_DATA);

        //List<School> list = new ArrayList<>();

        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作
        for (Map<Integer, String> map : mapList) {
            System.out.println(map);
            School school = new School();
            school.setName(map.get(0));
            school.setIntroduction(map.get(1));
            school.setCode(map.get(2));
            school.setLeaderName(map.get(3));
            school.setLeaderPhone(map.get(4));
            school.setLicense(map.get(5));
            school.setStudentNumber(Integer.valueOf(map.get(6)));
            String schoolType = map.get(7);
            if ("学校".equals(schoolType)) {
                school.setSchoolType(SchoolTypeEnum.PRIMARY_SCHOOL);
            }
            if ("机构".equals(schoolType)) {
                school.setSchoolType(SchoolTypeEnum.TRINING_INSTITUTION);
            }

            LocalDateTime time = LocalDateTime.now();
            school.setUpdateTime(time);
            school.setCreateTime(time);

            boolean isSuccess = save(school);
            Assert.isTrue(isSuccess, MessageCodes.SCHOOL_SAVE_ERROR);
            //list.add(school);
        }
        //Assert.isTrue(saveBatch(list), MessageCodes.SCHOOL_SAVE_ERROR);
        return true;
    }


    public Integer findSchoolIdBySchoolname(String schoolname){
        return  baseMapper.findSchoolIdBySchoolname(schoolname);
    }

}
