package com.luwei.service.child;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.child.Child;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.web.ChildBindingDTO;
import com.luwei.model.child.pojo.web.ChildEditDTO;
import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.model.parentChild.ParentChild;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.parentchild.ParentChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-11
 */
@Service
@Slf4j
public class ChildService extends ServiceImpl<ChildMapper, Child> {

    @Resource
    private ParentChildService parentChildService;

    @Resource
    private ChildService childService;

    public ChildWebVO findById(Integer id) {
        Child child = getById(id);
        org.springframework.util.Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);
        return toChildVO(child);
    }

    private ChildWebVO toChildVO(Child child) {
        ChildWebVO childVO = new ChildWebVO();
        BeanUtils.copyNonNullProperties(child, childVO);
        return childVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public ChildWebVO bindingChild(ChildBindingDTO childBindingDTO) {
        Child child = findChildByStunoAndNameAndSchoolId(childBindingDTO);

        Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);

        LocalDateTime time = LocalDateTime.now();

         try{
                //绑定孩子（更新中间表）
                ParentChild parentChild = new ParentChild();
                parentChild.setParentId(UserHelper.getUserId());
                parentChild.setChildId(child.getChildId());
                parentChild.setCreateTime(time);
                parentChildService.save(parentChild);
                log.info("保存数据---:{}", child);
         }catch (Exception e){
             throw new ValidationException(MessageCodes.CHILD_BINDING_ERROR);
         }

        return toChildVO(child);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChildWebVO updateChild(ChildEditDTO childUpdateDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childUpdateDTO, child);

        child.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(child), MessageCodes.CHILD_UPDATE_ERROR);
        log.info("修改数据：bean:{}", childUpdateDTO);
        return findById(child.getChildId());
    }

    //通过孩子的学号 姓名 学校id来查找这个学生
    private Child findChildByStunoAndNameAndSchoolId(ChildBindingDTO childBindingDTO) {
        return baseMapper.findChildByStunoAndNameAndSchoolId(childBindingDTO);
    }

}
