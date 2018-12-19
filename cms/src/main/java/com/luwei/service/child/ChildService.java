package com.luwei.service.child;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ReadExcelUtil;
import com.luwei.model.child.Child;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildAddDTO;
import com.luwei.model.child.pojo.cms.ChildQueryDTO;
import com.luwei.model.child.pojo.cms.ChildUpdateDTO;
import com.luwei.model.child.pojo.cms.ChildVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ffq
 * @since 2018-12-11
 */
@Service
@Slf4j
public class ChildService extends ServiceImpl<ChildMapper, Child> {


    public ChildVO findById(Integer id) {
        Child child = getById(id);
        //TODO记得修改MessageCodes
        org.springframework.util.Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);
        return toChildVO(child);
    }

    private ChildVO toChildVO(Child child) {
        ChildVO childVO = new ChildVO();
        BeanUtils.copyNonNullProperties(child, childVO);
        return childVO;
    }

    //finish
    @Transactional
    public ChildVO saveChild(ChildAddDTO childAddDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childAddDTO, child);
        LocalDateTime time = LocalDateTime.now();
        child.setUpdateTime(time);
        child.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        save(child);
        log.info("保存数据---:{}", child);
        return toChildVO(child);
    }

    //finish
    @Transactional
    public ChildVO updateChild(ChildUpdateDTO childUpdateDTO) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childUpdateDTO, child);

        child.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(child), MessageCodes.CHILD_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", childUpdateDTO);
        return findById(child.getChildId());
    }

    public void deleteChilds(Set<Integer> ids) {
        int count =baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.CHILD_DELETE_ERROR);
        log.info("删除数据:ids{}", ids);
    }

    public IPage<ChildVO> findPage(Page<Child> page,@Valid ChildQueryDTO childQueryDTO ) {
        return baseMapper.findPage(page,childQueryDTO);
    }


    public void importExcel(MultipartFile file) {
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        List<Map<Integer, String>> excelInfo = readExcelUtil.getExcelInfo(file);

        List<Child> list = new ArrayList<Child>();
        for (Map<Integer, String> map : excelInfo) {
            Child child = new Child();
            child.setName(map.get(0));
            child.setStudentNo(map.get(1));
            if("男".equals(map.get(2))){
                child.setGender(1);
            }else if("女".equals(map.get(2))){
                child.setGender(2);
            }else {
                child.setGender(0);
            }
            child.setSchoolName(map.get(3));
            child.setGrade(map.get(4));
            child.setChildClass(map.get(5));

            LocalDateTime time = LocalDateTime.now();
            child.setUpdateTime(time);
            child.setCreateTime(time);
            list.add(child);
        }
        boolean flag = saveBatch(list);
        Assert.isTrue(flag, MessageCodes.CHILD_IMPORT_FROM_EXCLE_ERROR);

    }


  /*
    public IPage<ChildVO> findChildPage(ChildQueryDTO childQueryDTO, Page page) {
        Child child = new Child();
        BeanUtils.copyNonNullProperties(childQueryDTO, child);
        QueryWrapper<Child> queryWrapper = new QueryWrapper<>();
        //查询业务
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toChildVO);
    }
    */



}
