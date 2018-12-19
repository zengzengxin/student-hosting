package com.luwei.service.child;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.child.Child;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.web.ChildAddDTO;
import com.luwei.model.child.pojo.web.ChildUpdateDTO;
import com.luwei.model.child.pojo.web.ChildVO;
import com.luwei.model.parentChild.ParentChild;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.parentchild.ParentChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ffq
 * @since 2018-12-11
 */
@Service
@Slf4j
public class ChildService extends ServiceImpl<ChildMapper, Child> {

    @Autowired
    private ParentChildService parentChildService;

    public ChildVO findById(Integer id) {
        Child child = getById(id);
        //TODO记得修改MessageCodes
        org.springframework.util.Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);
        return toChildVO(child);
    }

    private ChildVO toChildVO(Child child) {
        ChildVO childVO = new ChildVO();
        BeanUtils.copyNonNullProperties(child, childVO);
        return childVO;
    }

    //finish
    @Transactional
    public ChildVO saveChild(ChildAddDTO childAddDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childAddDTO, child);
        LocalDateTime time = LocalDateTime.now();
        child.setUpdateTime(time);
        child.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        save(child);

        //更新中间表
        ParentChild parentChild = new ParentChild();
        parentChild.setParentId(UserHelper.getUserId());
        parentChild.setChildId(child.getChildId());
        parentChild.setCreateTime(time);
        parentChildService.save(parentChild);
        log.info("保存数据---:{}", child);
        return toChildVO(child);
    }

    //finish
    @Transactional
    public ChildVO updateChild(ChildUpdateDTO childUpdateDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childUpdateDTO, child);

        child.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(child), MessageCodes.CHILD_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", childUpdateDTO);
        return findById(child.getChildId());
    }


  /*
    public IPage<ChildVO> findChildPage(ChildQueryDTO childQueryDTO, Page page) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childQueryDTO, child);
        QueryWrapper<Child> queryWrapper = new QueryWrapper<>();
        //查询业务
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toChildVO);
    }
    */



}
