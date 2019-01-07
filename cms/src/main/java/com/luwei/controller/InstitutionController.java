package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.institution.Institution;
import com.luwei.model.institution.pojo.cms.InstitutionAddDTO;
import com.luwei.model.institution.pojo.cms.InstitutionCmsVO;
import com.luwei.model.institution.pojo.cms.InstitutionQueryDTO;
import com.luwei.model.institution.pojo.cms.InstitutionUpdateDTO;
import com.luwei.service.institution.InstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Api(tags = "机构模块")
@RestController
@RequestMapping("/api/institution")
public class InstitutionController {

    @Resource
    private InstitutionService institutionService;

    @PostMapping
    @ApiOperation("新增")
    public InstitutionCmsVO saveInstitution(@RequestBody @Valid InstitutionAddDTO addDTO) {
        return institutionService.saveInstitution(addDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteInstitutions(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        institutionService.deleteInstitutions(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public InstitutionCmsVO updateInstitution(@RequestBody @Valid InstitutionUpdateDTO updateDTO) {
        return institutionService.updateInstitution(updateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public InstitutionCmsVO getInstitution(@RequestParam @ApiParam("id") Integer id) {
        return institutionService.getInstitution(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<InstitutionCmsVO> page(@ModelAttribute @Valid InstitutionQueryDTO queryDTO, Page<Institution> page) {
        return institutionService.findPage(queryDTO, page);
    }

}
