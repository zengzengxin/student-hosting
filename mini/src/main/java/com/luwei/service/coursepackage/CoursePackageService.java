package com.luwei.service.coursepackage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.model.teacher.Teacher;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.miniuser.MiniUserService;
import com.luwei.service.teacher.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
public class CoursePackageService extends ServiceImpl<CoursePackageMapper, CoursePackage> {

    @Resource
    private TeacherService teacherService;

    @Resource
    private MiniUserService miniUserService;

    public List<MyCourseVO> findAllByTime(LocalDateTime startTime, LocalDateTime endTime) {

        Integer userId = UserHelper.getUserId();
        MiniUser miniUser = miniUserService.getById(userId);
        Assert.notNull(miniUser, MessageCodes.MINIUSER_IS_NOT_EXIST);
        Teacher teacher = teacherService.getById(miniUser);
        Assert.notNull(teacher, MessageCodes.TEACHER_IS_NOT_EXIST);
        return baseMapper.findAllByTime(startTime, endTime, teacher.getTeacherId());

    }
}
