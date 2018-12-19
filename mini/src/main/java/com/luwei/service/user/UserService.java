package com.luwei.service.user;

import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.CommonSpecUtil;
import com.luwei.model.user.User;
import com.luwei.model.user.UserDao;
import com.luwei.model.user.pojo.UserPageVO;
import com.luwei.model.user.pojo.UserQueryVO;
import com.luwei.model.user.pojo.UserStateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 *
 * @author luwei
 **/
@Service
public class UserService {

    @Resource
    private CommonSpecUtil<User> specUtil;

    @Resource
    private UserDao userDao;

    /**
     * 获得用户列表
     *
     * @param pageable
     * @return
     */
    public Page<UserPageVO> findPage(UserQueryVO userQueryVO, Pageable pageable) {
        Specification<User> specifications = Specification.where(specUtil.equal("deleted", false))
                .and(specUtil.like("nickname", userQueryVO.getNickname()));
        return userDao.findAll(specifications, pageable).map(this::toUserPageVO);
    }

    /**
     * User -> UserPageVO
     *
     * @param user
     * @return UserPageVO
     */
    private UserPageVO toUserPageVO(User user) {
        UserPageVO pageVO = new UserPageVO();
        BeanUtils.copyProperties(user, pageVO);
        return pageVO;
    }

    @Transactional
    public UserPageVO handleDisabled(UserStateVO userStateVO) {
        User user = userDao.findById(userStateVO.getUserId()).get();
        Assert.notNull(user, MessageCodes.USER_NOT_EXIST);
        user.setDisabled(!user.getDisabled());
        userDao.save(user);
        return toUserPageVO(user);
    }

}
