package com.luwei.controller;


import com.luwei.model.HostingCommit.HostingCommit;
import com.luwei.model.HostingCommit.pojo.web.HostingCommitSaveDTO;
import com.luwei.model.courseCommit.CourseCommit;
import com.luwei.model.courseCommit.pojo.web.CourseCommitSaveDTO;
import com.luwei.service.CourseCommit.CourseCommitService;
import com.luwei.service.hostingCommit.HostingCommitService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/HostingCommit" )
public class HostingCommitControllerr {
    @Resource
    private HostingCommitService hostingCommitService;


    @PostMapping("save")
    public void save(@RequestBody @Valid HostingCommitSaveDTO hostingCommitSaveDTO){
        hostingCommitService.save(hostingCommitSaveDTO);
    }

    @GetMapping("delete")
    public void delete(@RequestParam("CommitId") Integer id){
        hostingCommitService.delete(id);
    }

    @GetMapping("faindAllHostingCommit")
    public List<HostingCommit> faindAllHostingCommit(@RequestParam("severId") Integer id){
        return hostingCommitService.faindAllHostingCommit(id);

    }

}
