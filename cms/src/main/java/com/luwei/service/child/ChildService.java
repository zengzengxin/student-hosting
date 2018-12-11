package com.luwei.service.child;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.child.Child;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildAddDTO;
import com.luwei.model.child.pojo.cms.ChildUpdateDTO;
import com.luwei.model.child.pojo.cms.ChildVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;



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

    //通过父母的id查询孩子的集合
    public List<ChildVO> findById(Integer id) {
        List<ChildVO> childList = baseMapper.findParents(id);
        //TODO记得修改MessageCodes
        Assert.notNull(childList, MessageCodes.CHILD_IS_NOT_EXIST);
        return childList;
    }

    private ChildVO toChildVO(Child child) {
        ChildVO childVO = new ChildVO();
        BeanUtils.copyNonNullProperties(child, childVO);
        return childVO;
    }

    @Transactional
    public ChildVO saveChild(ChildAddDTO childAddDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childAddDTO, child);
        LocalDateTime time = LocalDateTime.now();
        child.setUpdateTime(time);
        child.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        save(child);
        log.info("保存数据---:{}", child);
        return toChildVO(child);
    }

    @Transactional
    public ChildVO updateChild(ChildUpdateDTO childUpdateDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childUpdateDTO, child);

        child.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        //Assert.isTrue(updateById(child), MessageCodes.DATA_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", childUpdateDTO);
        // todo
        // return findById(child.getChildId());
        return null;
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
