package com.luwei.service.teacher;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.TeacherMapper;
import com.luwei.model.teacher.pojo.mini.TeacherMiniVO;
import com.luwei.model.teacher.pojo.web.TeacherEditDTO;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.miniuser.MiniUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Service
@Slf4j
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    @Resource
    private MiniUserService miniUserService;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Teacher findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Teacher teacher = getById(id);
        Assert.notNull(teacher, MessageCodes.TEACHER_IS_NOT_EXIST);
        return teacher;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param teacher
     * @return
     */
    private TeacherMiniVO toTeacherVO(Teacher teacher) {
        TeacherMiniVO teacherMiniVO = new TeacherMiniVO();
        BeanUtils.copyProperties(teacher, teacherMiniVO);
        return teacherMiniVO;
    }

    /**
     * 修改Teacher
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TeacherMiniVO updateTeacher(TeacherEditDTO updateDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(updateDTO, teacher);
        teacher.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(teacher), MessageCodes.TEACHER_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getTeacher();
    }

    /**
     * 获取单个Teacher
     *
     * @param
     * @return
     */
    public TeacherMiniVO getTeacher() {
        Integer id = UserHelper.getUserId();

        return baseMapper.findteacherById(id);
    }



    /*
     * 绑定手机号 ,更新miniuser
     * */
    public Teacher bindingTeacher(String phone, Integer id) {
        Teacher teacher = baseMapper.getTeacherByphone(phone);
        log.info(teacher.toString());
        Assert.notNull(teacher, MessageCodes.PHONE_IS_INVALID);

        Assert.isTrue(!teacher.getBinding(), MessageCodes.TEACHER_HAS_BINDING);
        //绑定老师
        MiniUser miniUser = miniUserService.getById(id);
        miniUser.setTeacherId(teacher.getTeacherId());
        LocalDateTime time = LocalDateTime.now();
        miniUser.setUpdateTime(time).setCreateTime(time).setDeleted(false);

        Assert.isTrue(miniUserService.updateById(miniUser), MessageCodes.TEACHER_BINDING_ERROR);

        //将老师的binding字段设置为1
        Teacher teacher1 = new Teacher();
        teacher1.setTeacherId(teacher.getTeacherId());
        teacher1.setBinding(true);
        boolean flag = updateById(teacher1);
        Assert.isTrue(flag, MessageCodes.TEACHER_UPDATE_ERROR);
        log.info("----微信用户绑定老师----");
        teacher.setBinding(flag);
        return teacher;

    }

}
