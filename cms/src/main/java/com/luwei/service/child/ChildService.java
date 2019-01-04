package com.luwei.service.child;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.common.util.ReadExcelUtil;
import com.luwei.model.child.Child;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildAddDTO;
import com.luwei.model.child.pojo.cms.ChildCmsVO;
import com.luwei.model.child.pojo.cms.ChildQueryDTO;
import com.luwei.model.child.pojo.cms.ChildUpdateDTO;
import com.luwei.model.manager.Manager;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.manager.ManagerService;
import com.luwei.service.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zzx
 * @since 2018-12-11
 */
@Service
@Slf4j
public class ChildService extends ServiceImpl<ChildMapper, Child> {

    @Resource
    private SchoolService schoolService;

    @Resource
    private ManagerService managerService;

    public ChildCmsVO findById(Integer id) {
        Child child = getById(id);
        org.springframework.util.Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);
        return toChildVO(child);
    }

    private ChildCmsVO toChildVO(Child child) {
        ChildCmsVO childVO = new ChildCmsVO();
        BeanUtils.copyNonNullProperties(child, childVO);
        return childVO;
    }

    //finish
    @Transactional(rollbackFor = Exception.class)
    public ChildCmsVO saveChild(ChildAddDTO childAddDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childAddDTO, child);
        LocalDateTime time = LocalDateTime.now();
        child.setUpdateTime(time);
        child.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        child.setSchoolName(schoolService.findById(childAddDTO.getSchoolId()).getName());
        save(child);
        log.info("保存数据---:{}", child);
        return toChildVO(child);
    }

    //finish
    @Transactional(rollbackFor = Exception.class)
    public ChildCmsVO updateChild(ChildUpdateDTO childUpdateDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childUpdateDTO, child);

        child.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(child), MessageCodes.CHILD_UPDATE_ERROR);
        log.info("修改数据：bean:{}", childUpdateDTO);
        return findById(child.getChildId());
    }

    public void deleteChildren(Set<Integer> ids) {
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.CHILD_DELETE_ERROR);
        log.info("删除数据:ids{}", ids);
    }

    public IPage<ChildCmsVO> findPage(Page<Child> page, @Valid ChildQueryDTO childQueryDTO) {
        Manager manager = managerService.getById(UserHelper.getUserId());
        if (manager.getRole() == RoleEnum.ROOT) {
            return ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<>()), this::toChildVO);
        }
        Integer schoolId = manager.getSchoolId();
        return baseMapper.findPage(page, childQueryDTO, schoolId);
    }

    public void importExcel(MultipartFile file) {
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        List<Map<Integer, String>> excelInfo = readExcelUtil.getExcelInfo(file);

        List<Child> list = new ArrayList<Child>();
        for (Map<Integer, String> map : excelInfo) {
            Child child = new Child();
            child.setName(map.get(0));
            child.setStudentNo(map.get(1));
            if ("男".equals(map.get(2))) {
                child.setGender(1);
            } else if ("女".equals(map.get(2))) {
                child.setGender(2);
            } else {
                child.setGender(0);
            }
            child.setSchoolName(map.get(3));
            child.setGrade(map.get(4));
            child.setChildClass(map.get(5));
            child.setSchoolId(schoolService.findSchoolIdBySchoolName(map.get(3)));

            LocalDateTime time = LocalDateTime.now();
            child.setUpdateTime(time);
            child.setCreateTime(time);
            list.add(child);
        }
        boolean flag = saveBatch(list);
        Assert.isTrue(flag, MessageCodes.CHILD_IMPORT_FROM_EXCEL_ERROR);

    }

}
