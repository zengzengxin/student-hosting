package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.cms.*;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageAddDTO;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageCmsVO;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageUpdateDTO;
import com.luwei.model.manager.Manager;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import com.luwei.model.school.School;
import com.luwei.model.teacher.Teacher;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.coursepackage.CoursePackageService;
import com.luwei.service.manager.ManagerService;
import com.luwei.service.picture.PictureService;
import com.luwei.service.recommend.RecommendService;
import com.luwei.service.school.SchoolService;
import com.luwei.service.teacher.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private PictureService pictureService;

    @Resource
    private RecommendService recommendService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private SchoolService schoolService;

    @Resource
    private ManagerService managerService;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Course findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Course course = getById(id);
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        return course;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param course
     * @return
     */
    private CourseCmsVO toCourseVO(Course course) {
        CourseCmsVO courseVO = new CourseCmsVO();
        BeanUtils.copyProperties(course, courseVO);
        return courseVO;
    }

    /**
     * 新增Course 并新增课程套餐
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CourseCmsVO saveCourse(CourseAddDTO addDTO) {
        // 保存课程
        Course course = new Course();
        BeanUtils.copyProperties(addDTO, course);
        LocalDateTime time = LocalDateTime.now();
        course.setUpdateTime(time);
        course.setCreateTime(time);
        Teacher teacher = teacherService.getById(addDTO.getTeacherId());
        course.setTeacherName(teacher.getTeacherName());
        School school = schoolService.getById(addDTO.getSchoolId());
        course.setSchoolName(school.getName());
        Assert.isTrue(save(course), MessageCodes.COURSE_SAVE_ERROR);
        Integer courseId = course.getCourseId();

        // 保存课程套餐
        List<CoursePackageAddDTO> temp = addDTO.getCoursePackageList();
        List<CoursePackageCmsVO> list = new ArrayList<>();
        for (CoursePackageAddDTO coursePackageAddDTO : temp) {
            CoursePackageCmsVO packageVO = saveCoursePackage(coursePackageAddDTO, course);
            list.add(packageVO);
        }

        // 保存课程图片
        List<String> urls = addDTO.getPictureUrls();
        for (String url : urls) {
            pictureService.savePicture(url, courseId, PictureTypeEnum.COURSE);
        }

        log.info("保存数据: {}", course);
        return toCourseVO(course).setPictureUrls(urls).setCoursePackageList(list);
    }

    private CoursePackageCmsVO saveCoursePackage(CoursePackageAddDTO addDTO, Course course) {

        if (addDTO.getClassTime().compareTo(addDTO.getQuittingTime()) >= 0) {
            throw new ValidationException(MessageCodes.CLASS_TIME_ERROR);
        }

        CoursePackage coursePackage = new CoursePackage();
        BeanUtils.copyProperties(addDTO, coursePackage);

        //处理开始时间,结束时间
        coursePackage.setStartTime(coursePackage.getStartTime().withHour(0).withMinute(0).withSecond(0));
        coursePackage.setEndTime(coursePackage.getEndTime().withHour(23).withMinute(59).withSecond(50));

        // 课程管理
        coursePackage.setCourseId(course.getCourseId());
        coursePackage.setCourseName(course.getCourseName());
        coursePackage.setTeacherId(course.getTeacherId());

        LocalDateTime time = LocalDateTime.now();
        coursePackage.setUpdateTime(time);
        coursePackage.setCreateTime(time);

        boolean success = coursePackageService.save(coursePackage);
        Assert.isTrue(success, MessageCodes.COURSE_PACKAGE_SAVE_ERROR);
        return toCoursePackageVO(coursePackage);
    }

    private CoursePackageCmsVO toCoursePackageVO(CoursePackage coursePackage) {
        CoursePackageCmsVO packageVO = new CoursePackageCmsVO();
        BeanUtils.copyProperties(coursePackage, packageVO);
        return packageVO;
    }

    /**
     * 批量删除Course
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourses(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.COURSE_DELETE_ERROR);
        log.info("删除数据: id {}", ids);

        // 删除推荐表数据
        for (Integer id : ids) {
            recommendService.realDeleteByServiceIdAndServiceType(id, ServiceTypeEnum.COURSE.getValue());
        }
        log.info("删除推荐表中课程ID为: {} 的数据", ids);
    }

    /**
     * 修改Course, 并修改课程套餐
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CourseCmsVO updateCourse(CourseUpdateDTO updateDTO) {
        // 修改课程
        Course course = new Course();
        BeanUtils.copyProperties(updateDTO, course);
        course.setUpdateTime(LocalDateTime.now());

        // ROOT修改不需要下架 学校修改则下架该课程 ---01-08 改需求
        Manager manager = managerService.getById(UserHelper.getUserId());
        if (manager.getRole() == RoleEnum.OPERATOR) {
            course.setDisplay(false);
        }

        Teacher teacher = teacherService.getById(updateDTO.getTeacherId());
        course.setTeacherName(teacher.getTeacherName());
        School school = schoolService.getById(updateDTO.getSchoolId());
        course.setSchoolName(school.getName());
        Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);
        course = getById(course.getCourseId());

        // 修改课程套餐
        List<CoursePackageUpdateDTO> temp = updateDTO.getCoursePackageList();
        List<CoursePackageCmsVO> list = new ArrayList<>();
        for (CoursePackageUpdateDTO coursePackageUpdateDTO : temp) {
            CoursePackageCmsVO packageVO = updateCoursePackage(coursePackageUpdateDTO, course);
            list.add(packageVO);
        }

        // 修改课程图片--先删除
        pictureService.deleteByPictureTypeAndForeignKeyId(PictureTypeEnum.COURSE.getValue(), course.getCourseId());
        // 保存课程图片
        List<String> urls = updateDTO.getPictureUrls();
        Integer courseId = course.getCourseId();
        for (String url : urls) {
            pictureService.savePicture(url, courseId, PictureTypeEnum.COURSE);
        }
        log.info("修改数据: bean {}", updateDTO);

        // 删除推荐表对应数据 ----01-09需求修改
        if (course.getRecommend()) {
            recommendService.realDeleteByServiceIdAndServiceType(courseId, ServiceTypeEnum.COURSE.getValue());
        }
        if (manager.getRole() == RoleEnum.ROOT && course.getRecommend()) {
            // 推荐表增一条数据
            CourseRecommend courseRecommend = new CourseRecommend();
            courseRecommend.setCourseId(courseId).setRecommend(true);
            recommend(courseRecommend);
        }

        return toCourseVO(course).setPictureUrls(urls).setCoursePackageList(list);
    }

    private CoursePackageCmsVO updateCoursePackage(CoursePackageUpdateDTO updateDTO, Course course) {

        // 课程套餐上架之后不可修改 -----2019-01-08 产品修改需求,可修改
        // CoursePackage p = coursePackageMapper.selectById(updateDTO.getCoursePackageId());
        // if (p != null && p.getDisplay()) {
        //     return toCoursePackageVO(p);
        // }

        if (updateDTO.getClassTime().compareTo(updateDTO.getQuittingTime()) >= 0) {
            throw new ValidationException(MessageCodes.CLASS_TIME_ERROR);
        }

        // 修改课程套餐时,也可以新增
        if (updateDTO.getCoursePackageId() == null) {
            CoursePackage coursePackage = new CoursePackage();
            BeanUtils.copyProperties(updateDTO, coursePackage);
            //处理开始时间,结束时间
            coursePackage.setStartTime(coursePackage.getStartTime().withHour(0).withMinute(0).withSecond(0));
            coursePackage.setEndTime(coursePackage.getEndTime().withHour(23).withMinute(59).withSecond(50));
            // 课程管理
            coursePackage.setCourseId(course.getCourseId());
            coursePackage.setCourseName(course.getCourseName());
            coursePackage.setTeacherId(course.getTeacherId());
            LocalDateTime time = LocalDateTime.now();
            coursePackage.setUpdateTime(time);
            coursePackage.setCreateTime(time);

            boolean success = coursePackageService.save(coursePackage);
            Assert.isTrue(success, MessageCodes.COURSE_PACKAGE_SAVE_ERROR);
            return toCoursePackageVO(coursePackage);
        }

        CoursePackage coursePackage = new CoursePackage();
        BeanUtils.copyProperties(updateDTO, coursePackage);
        //处理开始时间,结束时间
        coursePackage.setStartTime(coursePackage.getStartTime().withHour(0).withMinute(0).withSecond(0));
        coursePackage.setEndTime(coursePackage.getEndTime().withHour(23).withMinute(59).withSecond(50));

        coursePackage.setUpdateTime(LocalDateTime.now());
        boolean success = coursePackageService.updateById(coursePackage);
        Assert.isTrue(success, MessageCodes.COURSE_PACKAGE_UPDATE_ERROR);
        return toCoursePackageVO(coursePackageService.findById(coursePackage.getCoursePackageId()));
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public CourseCmsVO getCourse(Integer id) {
        return toCourseVO(findById(id));
    }

    /**
     * 分页获取Course
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IPage<CourseCmsVO> findPage(CourseQueryDTO queryDTO, Page<Course> page) {
        LambdaQueryWrapper<Course> wrapper = new QueryWrapper<Course>().lambda();
        // noinspection unchecked
        wrapper.orderByDesc(Course::getRecommend);
        if (queryDTO.getCourseName() != null && !queryDTO.getCourseName().equals("")) {
            wrapper.like(Course::getCourseName, queryDTO.getCourseName());
        }

        // 平台和教育局查所有课程, 学校查自己的课程
        Integer managerId = UserHelper.getUserId();
        Manager manager = managerService.getById(managerId);
        if (manager.getRole() == RoleEnum.OPERATOR) {
            wrapper.eq(Course::getSchoolId, manager.getSchoolId());
        }
        IPage<CourseCmsVO> iPage = ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toCourseVO);
        List<CourseCmsVO> list = iPage.getRecords();

        //设置图片和套餐
        List collect = list.stream().map(this::dealWith).collect(Collectors.toList());
        return iPage;
    }

    private CourseCmsVO dealWith(CourseCmsVO courseVO) {
        // 设置图片
        List<String> urls = pictureService.findAllByForeignKeyId(courseVO.getCourseId(), PictureTypeEnum.COURSE.getValue());

        // 设置课程套餐列表
        List<CoursePackageCmsVO> list = coursePackageService.listCmsVO(courseVO.getCourseId());

        return courseVO.setCoursePackageList(list).setPictureUrls(urls);
    }

    public CourseCmsVO recommend(CourseRecommend courseRecommend) {
        Course course = getById(courseRecommend.getCourseId());
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        if (courseRecommend.getRecommend()) {
            course.setRecommend(true);
            //saveOrUpdate(course);
            Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);
            // 最低价格
            BigDecimal minPrice = coursePackageService.findMinPriceByCourseId(course.getCourseId());
            Recommend recommend = new Recommend();
            recommend.setServiceId(course.getCourseId())
                    .setServiceName(course.getCourseName())
                    .setServicePrice(minPrice)
                    .setServiceIntroduction(course.getIntroduction())
                    .setServiceCoverUrl(course.getCoverUrl())
                    .setWeight(1)
                    .setSchoolId(course.getSchoolId())
                    .setServiceType(ServiceTypeEnum.COURSE);

            // 创建时间 更新时间
            LocalDateTime now = LocalDateTime.now();
            recommend.setCreateTime(now).setUpdateTime(now);
            recommendService.save(recommend);
            return toCourseVO(course);
        }

        // 删除一条推荐数据
        course.setRecommend(false);
        Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);
        recommendService.realDeleteByServiceIdAndServiceType(course.getCourseId(), ServiceTypeEnum.COURSE.getValue());

        return toCourseVO(course);
    }

    //上下架
    public CourseCmsVO display(CourseDisplay courseDisplay) {
        Course course = getById(courseDisplay.getCourseId());
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        if (courseDisplay.getDisplay()) {
            course.setDisplay(true);
            Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);
        } else {
            course.setDisplay(false);

            // 去掉推荐
            course.setRecommend(false);
            recommend(new CourseRecommend()
                    .setCourseId(course.getCourseId())
                    .setRecommend(false));

            Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);
        }
        return toCourseVO(course);
    }

}
