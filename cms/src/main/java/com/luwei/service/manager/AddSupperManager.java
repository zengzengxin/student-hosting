package com.luwei.service.manager;

import com.luwei.common.util.BcryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Base64;

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

    /*@Resource
    private ManagerMapper managerMapper;

    @Value("${luwei.config.salt}")
    private String salt;

    @Override
    public void run(String... args) {
        Manager manager = managerMapper.findByAccountAndDeletedIsFalse("luwei");
        if (Objects.isNull(manager)) {
            manager = new Manager();
            manager.setAccount("luwei");
            manager.setPassword(DigestUtils.md5DigestAsHex(("luwei" + salt).getBytes()));
            manager.setName("ROOT");
            manager.setRole(RoleEnum.ROOT);
            managerMapper.insert(manager);
        }
    }*/

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            final String luwei =  encoder.encodeToString(BcryptUtil.encrypt("luwei"));
            System.out.println(luwei);
            System.out.println(BcryptUtil.decrypt(luwei));
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
