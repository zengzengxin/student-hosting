package com.luwei.model.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jdq
 * @date 2017/12/7 11:55
 */
public interface ManagerDao extends JpaRepository<Manager, Integer>, JpaSpecificationExecutor<Manager> {

    Manager findByAccountAndPasswordAndDeletedIsFalse(String account, String md5Password);

    Manager findByAccountAndDeletedIsFalse(String account);

    Manager findByManagerIdAndDeletedIsFalse(Integer managerId);

}
