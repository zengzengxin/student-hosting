package com.luwei.models.user.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jdq
 * @date 2017/12/15 14:21
 */
public interface UserViewDao extends JpaRepository<UserView, Integer>, JpaSpecificationExecutor<UserView> {

    UserView findByUserIdAndProtoId(int userId, int protoId);

}
