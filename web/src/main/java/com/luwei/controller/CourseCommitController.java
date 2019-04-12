package com.luwei.controller;


import com.luwei.model.courseCommit.CourseCommit;
import com.luwei.model.courseCommit.pojo.web.CourseCommitSaveDTO;
import com.luwei.service.CourseCommit.CourseCommitService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/CourseCommit" )
public class CourseCommitController {

    @Resource
    private CourseCommitService courseCommitService;


    @PostMapping("save")
    public void save(@RequestBody @Valid CourseCommitSaveDTO courseCommitSaveDTO){
        courseCommitService.save(courseCommitSaveDTO);
    }

    @GetMapping("delete")
    public void delete(@RequestParam("CommitId") Integer id){
        courseCommitService.delete(id);
    }

    @GetMapping("faindAllCommit")
    public List<CourseCommit> faindAllCommit(@RequestParam("severId") Integer id){
        return courseCommitService.faindAllCommit(id);

    }
}
