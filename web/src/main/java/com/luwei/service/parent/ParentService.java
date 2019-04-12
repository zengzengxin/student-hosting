package com.luwei.service.parent;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.model.institution.Institution;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.ParentMapper;
import com.luwei.model.parent.pojo.web.ParentAddDTO;
import com.luwei.model.parent.pojo.web.ParentEditDTO;
import com.luwei.model.parent.pojo.web.ParentLoginDTO;
import com.luwei.model.parent.pojo.web.ParentWebVO;
import com.luwei.module.shiro.service.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zzx
 * @since 2018-12-12
 */
@Service
@Slf4j
public class ParentService extends ServiceImpl<ParentMapper, Parent> {

    @Resource
    private ChildMapper childMapper;

    @Resource
    private ParentMapper parentMapper;

    @Resource
    CacheManager cacheManager;

    private Cache cache = cacheManager.getCache("parent");

    @Cacheable(cacheNames = "parent")
    public ParentWebVO findParentById(Integer id) {
        Parent parent = getById(id);
        Assert.notNull(parent, MessageCodes.PARENT_IS_NOT_EXIST);
        return toParentVO(parent);
    }

    private ParentWebVO toParentVO(Parent parent) {
        ParentWebVO parentVO = new ParentWebVO();
        BeanUtils.copyNonNullProperties(parent, parentVO);
        return parentVO;
    }

    //更新
    @Transactional(rollbackFor = Exception.class)
    @CachePut(cacheNames = "parent",key="#parentUpdateDTO.parentId")
    public ParentWebVO updateParent(ParentEditDTO parentUpdateDTO) {
        Parent parent = new Parent();
        BeanUtils.copyNonNullProperties(parentUpdateDTO, parent);
        parent.setUpdateTime(LocalDateTime.now());


        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(parent), MessageCodes.PARENT_UPDATE_ERROR);
        log.info("修改数据：bean:{}", parentUpdateDTO);
        return findParentById(parent.getParentId());
    }

    /**
     * 通过父母的id查询孩子的集合
     *
     * @param parentId
     * @return
     */
    public List<ChildWebVO> findAllParentById(Integer parentId) {
        List<ChildWebVO> childList = childMapper.webFindChildsByParentsId(parentId);
        Assert.notNull(childList, MessageCodes.CHILD_IS_NOT_EXIST);
        return childList;
    }

    public Parent findByOpenid(String openId) {
        return parentMapper.findByOpenid(openId);
    }

    public ParentWebVO login(ParentLoginDTO parentLoginDTO) {
        ParentWebVO parentWebVO = new ParentWebVO();
        LambdaQueryWrapper<Parent> wrapper = new QueryWrapper<Parent>().lambda();
        wrapper.eq(Parent::getUserName, parentLoginDTO.getUserName());
        wrapper.eq(Parent::getPassword, parentLoginDTO.getPassword());
        List<Object> objects = parentMapper.selectObjs(wrapper);
        if (objects.size()==0){
            Assert.notNull(null, MessageCodes.PARENT_IS_NOT_EXIST);
        }
             BeanUtils.copyNonNullProperties(objects.get(0),parentWebVO );
             return parentWebVO;

    }

    public void save(ParentAddDTO parentAddDTO) {
        Parent parent = new Parent();
        parent.setParentName(parentAddDTO.getParentName());
        parent.setPhone(parentAddDTO.getPhone());
        parent.setUserName(parentAddDTO.getUserName());
        parent.setPassword(parentAddDTO.getPassword());
        parentMapper.insert(parent);
    }
}
