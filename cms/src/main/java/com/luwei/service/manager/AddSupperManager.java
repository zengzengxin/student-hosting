package com.luwei.service.manager;

import com.luwei.common.constants.RoleEnum;
import com.luwei.common.utils.BcryptUtil;
import com.luwei.models.manager.Manager;
import com.luwei.models.manager.ManagerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Objects;

/**
 * description
 * create by LW-mochengdong
 * 2018/6/22
 */
@Slf4j
@Service
public class AddSupperManager implements CommandLineRunner {

    private static Base64.Encoder encoder = Base64.getEncoder();

    private static Base64.Decoder decoder = Base64.getDecoder();

    @Resource
    private ManagerDao managerDao;

    @Value("${luwei.config.salt}")
    private String salt;

    @Override
    public void run(String... args) {
        Manager manager = managerDao.findByAccountAndDeletedIsFalse("luwei");
        if (Objects.isNull(manager)) {
            manager = new Manager();
            manager.setAccount("luwei");
            manager.setPassword(DigestUtils.md5DigestAsHex(("luwei" + salt).getBytes()));
            manager.setName("ROOT");
            manager.setRole(RoleEnum.ROOT);
            managerDao.save(manager);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final String luwei =  encoder.encodeToString(BcryptUtil.encrypt("test"));
            System.out.println(luwei);
            System.out.println(BcryptUtil.decrypt(luwei));
        }
    }
}
