package com.luwei.service.miniuser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.model.miniuser.MiniUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ffq
 * @since 2018-12-20
 */
@Service
@Slf4j
public class MiniUserService extends ServiceImpl<MiniUserMapper, MiniUser> {


    public MiniUser findUserByOpenId(String openId) {

        MiniUser miniUser = baseMapper.findUserByOpenId(openId);
        return miniUser;
    }


   /* public MiniUserCmsVO findById(Integer miniuserId) {
        MiniUser miniuser = getById(miniuserId);
        //TODO记得修改MessageCodes
        Assert.notNull(miniuser, MessageCodes.DATA_IS_NOT_EXIST);
        return toMiniUserCmsVO(miniuser);
    }

    private MiniUserCmsVO toMiniUserCmsVO(MiniUser miniuser) {
        MiniUserCmsVO miniuserVO = new MiniUserCmsVO();
        BeanUtils.copyNonNullProperties(miniuser, miniuserVO);
        return miniuserVO;
    }

    @Transactional
    public MiniUserCmsVO saveMiniUser(MiniUser miniuser) {
        LocalDateTime time = LocalDateTime.now();
        miniuser.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(miniuser);
        Assert.isTrue(isSuccess, MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据---:{}", miniuser);
        return toMiniUserCmsVO(miniuser);
    }

    @Transactional
    public void deleteMiniUsers(Set<Integer> miniuserIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(miniuserIds);
        Assert.isTrue(count == miniuserIds.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据:ids{}", miniuserIds);
    }

    @Transactional
    public MiniUserCmsVO updateMiniUser(MiniUserCmsUpdateDTO miniuserUpdateDTO) {
        MiniUser miniuser = BeanUtils.copyNonNullProperties(miniuserUpdateDTO, MiniUser.class);
        miniuser.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(miniuser), MessageCodes.DATA_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", miniuserUpdateDTO);
        return findById(miniuser.getMiniUserId());
    }

    public IPage<MiniUserCmsVO> findMiniUserPage(MiniUserCmsQueryDTO miniuserQueryDTO, Page page) {
        MiniUser miniuser = BeanUtils.copyNonNullProperties(miniuserQueryDTO, MiniUser.class);
        QueryWrapper<MiniUser> queryWrapper = new QueryWrapperDecorator<>();
        //查询业务
        queryWrapper.lambda().eq(MiniUser::getMiniUserId, miniuser.getMiniUserId());
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toMiniUserCmsVO);
    }*/
}
