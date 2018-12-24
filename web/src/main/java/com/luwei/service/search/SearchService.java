package com.luwei.service.search;

import com.luwei.model.course.Course;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.search.SearchVO;
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
    CourseService courseService;

    @Resource
    HostingService hostingService;

    public List<SearchVO> findSever() {
        List<SearchVO> searchVOS = new ArrayList<SearchVO>();

        List<Course> Courselist = courseService.findList();
        for (Course c : Courselist) {
            SearchVO searchVO = new SearchVO();
            searchVO.setSeverId(c.getCourseId());
            searchVO.setSeverName(c.getCourseName());
            searchVO.setSeverType(0);
            searchVOS.add(searchVO);
        }

        List<Hosting> hostinglist = hostingService.findList();
        for (Hosting h : hostinglist) {
            SearchVO searchVO = new SearchVO();
            searchVO.setSeverId(h.getHostingId());
            searchVO.setSeverName(h.getName());
            searchVO.setSeverType(1);
            searchVOS.add(searchVO);
        }

        return searchVOS;
    }
}
