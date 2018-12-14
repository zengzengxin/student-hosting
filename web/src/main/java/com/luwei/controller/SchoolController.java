package com.luwei.controller;

import com.luwei.model.school.pojo.web.SchoolWebVO;
import com.luwei.service.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zzx
 * @since 2018-12-13
 */
@Api(tags = {"机构管理"})
@RestController
@RequestMapping("/api/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;


}

