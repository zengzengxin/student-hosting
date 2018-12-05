package com.luwei.controllers;

import com.luwei.service.manager.LoginService;
import com.luwei.service.manager.pojos.LoginSuccessVO;
import com.luwei.service.manager.pojos.ManagerLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 *
 * @author luwei
 **/
@RestController
@Api(tags = "登录模块")
@RequestMapping("/api/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping
    public LoginSuccessVO login(@RequestBody @Valid ManagerLoginVO loginVO) {
        return loginService.login(loginVO);
    }

}
