package com.luwei.service.school;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import com.luwei.model.school.pojo.web.SchoolWebVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-13
 */
@Service
@Slf4j
public class SchoolService extends ServiceImpl<SchoolMapper, School> {


}
