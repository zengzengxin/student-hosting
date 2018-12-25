package com.luwei.service.parent;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.ParentMapper;
import com.luwei.model.parent.pojo.web.ParentUpdateDTO;
import com.luwei.model.parent.pojo.web.ParentwebVO;
import com.luwei.module.shiro.service.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ffq
 * @since 2018-12-12
 */
@Service
@Slf4j
public class ParentService extends ServiceImpl<ParentMapper, Parent> {

    @Resource
    private ChildMapper childMapper;

    @Resource
    private ParentMapper parentMapper;

    public ParentwebVO findParentById(Integer id) {
        Parent parent = getById(id);
        //TODO记得修改MessageCodes
        Assert.notNull(parent, MessageCodes.PARENT_IS_NOT_EXIST);
        return toParentVO(parent);
    }

    private ParentwebVO toParentVO(Parent parent) {
        ParentwebVO parentVO = new ParentwebVO();
        BeanUtils.copyNonNullProperties(parent, parentVO);
        return parentVO;
    }


    //更新
    @Transactional
    public ParentwebVO updateParent(ParentUpdateDTO parentUpdateDTO) {
        Parent parent = new Parent();
        BeanUtils.copyNonNullProperties(parentUpdateDTO, parent);
        parent.setUpdateTime(LocalDateTime.now());

        parent.setParentId(UserHelper.getUserId());
        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(parent), MessageCodes.PARENT_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", parentUpdateDTO);
        return findParentById(parent.getParentId());
    }




    /**
     * 通过父母的id查询孩子的集合
     * @param parentId
     * @return
     */
    public List<ChildWebVO> findAllParentById(Integer parentId) {
        List<ChildWebVO> childList = childMapper.webFindChildsByParentsId(parentId);
        // TODO 记得修改MessageCodes
        Assert.notNull(childList, MessageCodes.CHILD_IS_NOT_EXIST);
        return childList;
    }

    public Parent findByOpenid(String openId) {
        return parentMapper.findByOpenid(openId);
    }


    /*    //添加家长
    @Transactional
    public Parent saveParent(ParentAddDTO addDTO) {
        Parent parent = new Parent();
        BeanUtils.copyNonNullProperties(addDTO, parent);
        LocalDateTime time = LocalDateTime.now();
        parent.setUpdateTime(time);
        parent.setCreateTime(time);
        save(parent);
        log.info("保存数据---:{}", parent);
        return parent;
    }*/

/*    @Transactional
    public void deleteParents(Set<Integer> ids) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据:id{}", ids);
    }*/


/*   public IPage<ParentwebVO> findParentPage(ParentQueryDTO parentQueryDTO, Page<Parent> page) {
        Parent parent = new Parent();
        BeanUtils.copyNonNullProperties(parentQueryDTO, parent);
        QueryWrapper<Parent> queryWrapper = new QueryWrapper<>();
        //查询业务
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toParentVO);
    }*/
}
