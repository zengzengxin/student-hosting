package com.luwei.service.hostingCommit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luwei.model.HostingCommit.HostingCommit;
import com.luwei.model.HostingCommit.HostingCommitMapper;
import com.luwei.model.HostingCommit.pojo.web.HostingCommitSaveDTO;
import com.luwei.model.courseCommit.CourseCommit;
import com.luwei.model.courseCommit.CourseCommitMapper;
import com.luwei.model.courseCommit.pojo.web.CourseCommitSaveDTO;
import com.luwei.model.hosting.HostingMapper;
import com.luwei.model.parent.Parent;
import com.luwei.service.parent.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class HostingCommitService {

    @Resource
    ParentService parentService;

    @Resource
    HostingCommitMapper hostingCommitMapper;

    public void save(HostingCommitSaveDTO hostingCommitSaveDTO) {
        Parent parent = parentService.getById(hostingCommitSaveDTO.getParentId());
        HostingCommit hostingCommit = new HostingCommit();
        hostingCommit.setHostingId(hostingCommitSaveDTO.getCourseId());
        hostingCommit.setParentId(hostingCommitSaveDTO.getParentId());
        hostingCommit.setContant(hostingCommitSaveDTO.getContant());
        hostingCommit.setParentName(parent.getParentName());
        hostingCommitMapper.insert(hostingCommit);
    }

    public void delete(int id) {
        hostingCommitMapper.deleteById(id);
    }

    public List<HostingCommit> faindAllHostingCommit(Integer id) {

        QueryWrapper<HostingCommit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HostingCommit::getHostingId,id);
        return hostingCommitMapper.selectList(queryWrapper);
    }
}
