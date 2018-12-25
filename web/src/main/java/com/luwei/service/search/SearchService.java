package com.luwei.service.search;

import com.luwei.model.course.Course;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import com.luwei.model.search.SearchWebVO;
import com.luwei.service.course.CourseService;
import com.luwei.service.hosting.HostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huanglp
 * Date: 2018-12-24
 */
@Service
@Slf4j
public class SearchService {

    @Resource
    private CourseService courseService;

    @Resource
    private HostingService hostingService;

    public List<SearchWebVO> findSever() {
        List<SearchWebVO> searchVOS = new ArrayList<>();

        List<Course> courseList = courseService.findList();
        for (Course c : courseList) {
            SearchWebVO searchVO = new SearchWebVO();
            searchVO.setSeverId(c.getCourseId());
            searchVO.setSeverName(c.getCourseName());
            searchVO.setSeverType(ServiceTypeEnum.COURSE);
            searchVOS.add(searchVO);
        }

        List<Hosting> hostingList = hostingService.findList();
        for (Hosting h : hostingList) {
            SearchWebVO searchVO = new SearchWebVO();
            searchVO.setSeverId(h.getHostingId());
            searchVO.setSeverName(h.getName());
            searchVO.setSeverType(ServiceTypeEnum.HOSTING);
            searchVOS.add(searchVO);
        }

        return searchVOS;
    }
}
