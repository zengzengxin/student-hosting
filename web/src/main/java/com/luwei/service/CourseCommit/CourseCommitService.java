package com.luwei.service.CourseCommit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luwei.model.courseCommit.CourseCommit;
import com.luwei.model.courseCommit.CourseCommitMapper;
import com.luwei.model.courseCommit.pojo.web.CourseCommitSaveDTO;
import com.luwei.model.parent.Parent;
import com.luwei.service.parent.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CourseCommitService {

    @Resource
    ParentService parentService;

    @Resource
    CourseCommitMapper courseCommitMapper;

    public void save(CourseCommitSaveDTO courseCommitSaveDTO) {
        Parent parent = parentService.getById(courseCommitSaveDTO.getParentId());
        CourseCommit courseCommit = new CourseCommit();
        courseCommit.setCourseId(courseCommitSaveDTO.getCourseId());
        courseCommit.setParentId(courseCommitSaveDTO.getParentId());
        courseCommit.setParentName(parent.getParentName());
        courseCommit.setContant(courseCommitSaveDTO.getContant());
        courseCommitMapper.insert(courseCommit);
    }

    public void delete(int id) {
        courseCommitMapper.deleteById(id);
    }

    public List<CourseCommit> faindAllCommit(Integer id) {
        QueryWrapper<CourseCommit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CourseCommit::getCourseId,id);
        return courseCommitMapper.selectList(queryWrapper);
    }
}
