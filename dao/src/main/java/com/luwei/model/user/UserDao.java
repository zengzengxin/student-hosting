package com.luwei.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jdq
 * @date 2017/12/7 11:56
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    
}
