package com.luwei.service.school;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ReadExcelUtil;
import com.luwei.common.util.ValidUtils;
import com.luwei.model.manager.Manager;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import com.luwei.model.school.pojo.cms.SchoolCmsVO;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.model.teacher.Teacher;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.manager.ManagerService;
import com.luwei.service.teacher.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zzx
 * @since 2018-12-13
 */
@Service
@Slf4j
public class SchoolService extends ServiceImpl<SchoolMapper, School> {

    @Resource
    private ManagerService managerService;

    @Resource
    private TeacherService teacherService;

    public SchoolCmsVO findById(Integer schoolId) {
        School school = getById(schoolId);
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        return toSchoolVO(school);
    }

    private SchoolCmsVO toSchoolVO(School school) {
        SchoolCmsVO schoolVO = new SchoolCmsVO();
        BeanUtils.copyNonNullProperties(school, schoolVO);
        return schoolVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSchools(Set<Integer> schoolIds) {
        // 如果学校已经绑定了老师，就不能删除 -----2019-01-15
        for (Integer schoolId : schoolIds) {
            int count = teacherService.count(new QueryWrapper<Teacher>().eq("school_id", schoolId).eq("deleted", false));
            Assert.isTrue(count > 0, MessageCodes.SCHOOL_CANNOT_DELETE);
        }

        int count = baseMapper.deleteBatchIds(schoolIds);
        Assert.isTrue(count == schoolIds.size(), MessageCodes.SCHOOL_DELETE_ERROR);
        log.info("删除数据:ids{}", schoolIds);
    }

    public IPage<SchoolCmsVO> findSchoolPage(SchoolQueryDTO schoolQueryDTO, Page page) {
        return baseMapper.getSchoolPage(page, schoolQueryDTO);
    }

    public List<SchoolCmsVO> findSchoolPage() {
        LambdaQueryWrapper<School> wrapper = new QueryWrapper<School>().lambda();
        // 如果学校角色调用此方法, 则只返回此学校的数据
        Integer managerId = UserHelper.getUserId();
        Manager manager = managerService.getById(managerId);
        if (manager.getRole() == RoleEnum.OPERATOR) {
            wrapper.eq(School::getSchoolId, manager.getSchoolId());
        }
        return list(wrapper).stream().map(this::toSchoolVO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean readExcelFile(MultipartFile file) {
        String result;

        //解析excel，获取上传的事件单
        List<Map<Integer, String>> mapList = ReadExcelUtil.getExcelInfo(file);
        Assert.notNull(mapList, MessageCodes.EXCEL_HAS_ERROR_DATA);

        //List<School> list = new ArrayList<>();
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作
        for (Map<Integer, String> map : mapList) {
            // 可能出现excel空行的情况，则跳过
            if (StringUtils.isEmpty(map.get(0))) {
                continue;
            }
            School school = new School();
            school.setName(map.get(0));
            school.setIntroduction(map.get(1));
            school.setCode(map.get(2));
            school.setLeaderName(map.get(3));
            school.setLeaderPhone(map.get(4));
            school.setStudentNumber(Integer.valueOf(map.get(5)));
            // 校验手机号
            boolean isPhone = ValidUtils.isPhone(school.getLeaderPhone());
            Assert.isTrue(isPhone, MessageCodes.INVALID_PHONE_NUMBER);

            // 01-07需求变更: 暂不需要此字段
            // String schoolType = map.get(6);
            // if ("0".equals(schoolType)) school.setSchoolType(SchoolTypeEnum.PRIMARY_SCHOOL);
            // if ("1".equals(schoolType)) school.setSchoolType(SchoolTypeEnum.TRINING_INSTITUTION);
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

    public Integer findSchoolIdBySchoolName(String schoolName) {
        Integer schoolId = baseMapper.findSchoolIdBySchoolName(schoolName);
        Assert.notNull(schoolId, MessageCodes.SCHOOL_IS_NOT_EXIST);
        return schoolId;
    }

}
