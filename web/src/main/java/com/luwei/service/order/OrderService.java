package com.luwei.service.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.property.WechatPayPackage;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.common.util.OrderIdUtils;
import com.luwei.common.util.WeChatUtils;
import com.luwei.model.child.Child;
import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.web.CourseBuyDTO;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.pojo.web.HostingBuyDTO;
import com.luwei.model.hosting.pojo.web.HostingWebVO;
import com.luwei.model.order.Order;
import com.luwei.model.order.OrderMapper;
import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import com.luwei.model.order.envm.PaymentEnum;
import com.luwei.model.order.pojo.cms.OrderCmsVO;
import com.luwei.model.order.pojo.web.*;
import com.luwei.model.parent.Parent;
import com.luwei.model.recommend.Recommend;
import com.luwei.module.pay.model.WXNotifyResultVo;
import com.luwei.module.pay.service.WXPayNotifyService;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.child.ChildService;
import com.luwei.service.course.CourseService;
import com.luwei.service.coursepackage.CoursePackageService;
import com.luwei.service.hosting.HostingService;
import com.luwei.service.parent.ParentService;
import com.luwei.service.recommend.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> implements WXPayNotifyService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    private ParentService parentService;

    @Resource
    private ChildService childService;

    @Resource
    private CourseService courseService;

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private HostingService hostingService;

    @Resource
    private WeChatUtils weChatUtils;

    @Resource
    private RecommendService recommendService;

    @Value("${luwei.module.pay.wechat.notifyUrl}")
    private String notifyUrl;

    @Resource
    CacheManager cacheManager;

    private Cache cache = cacheManager.getCache("order");
    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Order findById(String id) {
        // 若此id已被逻辑删除,也会返回null
        Order order = getById(id);
        Assert.notNull(order, MessageCodes.ORDER_IS_NOT_EXIST);
        return order;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param order
     * @return
     */
    private OrderCmsVO toOrderVO(Order order) {
        OrderCmsVO orderVO = new OrderCmsVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    /**
     * 确认下单/立即购买(托管)
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderCmsVO hostingOrder(HostingOrderDTO hostingOrderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(hostingOrderDTO, order);

        // 封装课程数据
        Hosting hosting = hostingService.getById(order.getServiceId());

        Integer parentId = UserHelper.getUserId();
        Parent parent = parentService.getById(parentId);
        Child child = childService.getById(hostingOrderDTO.getChildId());

        //判断所选时间是否有效
        if (hosting.getStartTime().compareTo(hostingOrderDTO.getStartTime()) > 0 || hosting.getEndTime().compareTo(hostingOrderDTO.getEndTime()) < 0) {
            throw new ValidationException(MessageCodes.ORDER_TIME_ERROR);
        }

        //判断课程是否已经到达最大人数
        if (hosting.getApplicantsNumber() >= hosting.getMaxNumber()) {
            throw new ValidationException(MessageCodes.APPLICANTS_NUMBER_ENOUGH);
        }
        //判断孩子是不是家长的孩子
        Boolean flag = false;
        List<ChildWebVO> childList = parentService.findAllParentById(parentId);
        for (ChildWebVO c : childList) {
            if (c.getChildId().equals(hostingOrderDTO.getChildId())) {
                flag = true;
            }
        }

        Assert.isTrue(flag, MessageCodes.ORDER_CHILD_ERROR);


        //设置关于托管班的信息
        order.setServiceName(hosting.getName());
        order.setServiceCover(hosting.getCoverUrl());
        order.setIntroduction(hosting.getIntroduction());
        order.setServiceId(hosting.getHostingId());
        order.setSchoolId(hosting.getSchoolId());
        order.setSchoolName(hosting.getSchoolName());

        //设置关于家长的信息
        order.setParentId(parent.getParentId());
        order.setParentPhone(parent.getPhone());
        order.setParentName(parent.getParentName());
        order.setNickName(parent.getNickName());

        //设置孩子相关的信息
        order.setChildId(child.getChildId());
        order.setChildName(child.getName());
        order.setChildStudentNo(child.getStudentNo());
        order.setChildGrade(child.getGrade());
        order.setChildClass(child.getChildClass());

        //开始时间与结束时间
        order.setServiceStartTime(hostingOrderDTO.getStartTime());
        order.setServiceEndTime(hostingOrderDTO.getEndTime());

        //设置价格
        BigDecimal severPrice = hostingService.getById(hostingOrderDTO.getServiceId()).getPrice();
        order.setPrice(severPrice.multiply(BigDecimal.valueOf(getDays(hostingOrderDTO.getStartTime(), hostingOrderDTO.getEndTime()))));

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);

        // 01-07新增: 教师ID,教师名称,上课时间,下课时间
        order.setTeacherId(hosting.getTeacherId())
                .setTeacherName(hosting.getTeacherName());

        // 订单类型, 订单状态
        order.setOrderType(OrderTypeEnum.HOSTING)
                .setOrderStatus(OrderStatusEnum.NOT_PAID);

        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTimestamp());
        System.out.println(order.getOrderId());

        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);
    }

    /**
     * 计算天数, 排除周六周日
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public long getDays(LocalDateTime startTime, LocalDateTime endTime) {

        // 方式一
        class MyCount {
            private long count;
        }
        MyCount myCount = new MyCount();
        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = endTime.toLocalDate();
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance > 1000) {
            Assert.isTrue(false, MessageCodes.DATE_IS_TO_LONG);
        }
        if (distance < 0) {
            return 0;
        }
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> {
            // 不计算周六日
            if (f.getDayOfWeek() != DayOfWeek.SATURDAY && f.getDayOfWeek() != DayOfWeek.SUNDAY) {
                myCount.count++;
            }
        });
        return myCount.count;

/*        // 方式二
        int startWeek;  //开始日期
        int endWeek;    //结束日期
        int offset;     //偏移量
        long days;      //两个时间之间的天数
        long workDays;  //工作日

        //计算偏移量
        if (startTime.getDayOfWeek().getValue() != 6 && startTime.getDayOfWeek().getValue() != 7) {
            startWeek = startTime.getDayOfWeek().getValue();
        } else {
            startWeek = 5;
        }

        if (endTime.getDayOfWeek().getValue() != 6 && endTime.getDayOfWeek().getValue() != 7) {
            endWeek = endTime.getDayOfWeek().getValue();
        } else {
            endWeek = 5;
        }

        if (startWeek < endWeek) {
            offset = endWeek - startWeek + 1;
        } else if (startWeek > endWeek) {
            offset = 5 - startWeek + 1 + endWeek;
        } else {
            offset = 1;
        }
        if (offset == 5) {
            offset = 0;
        }

        Duration be = Duration.between(startTime, endTime);
        days = be.toDays();
        workDays = ((days + 1) / 7) * 5 + offset;

        return workDays;*/
    }

    /*
     *
     * 确认下单/立即购买
     * */
    @Transactional(rollbackFor = Exception.class)
    public OrderCmsVO confirmOrder(ConfirmOrderDTO orderDTO) {
        Order order = new Order();
        Integer parentId = UserHelper.getUserId();
        Course course = courseService.getById(orderDTO.getServiceId());
        CoursePackage cp = coursePackageService.getById(orderDTO.getPackageId());

        //判断课程是否已经到达最大人数
        if (cp.getApplicantsNumber() >= cp.getMaxNumber()) {
            Assert.isTrue(false, MessageCodes.APPLICANTS_NUMBER_ENOUGH);
        }


        // 判断该家长是否绑定此学生
        Boolean flag = false;
        List<ChildWebVO> childList = parentService.findAllParentById(parentId);
        for (ChildWebVO c : childList) {
            if (c.getChildId().equals(orderDTO.getChildId())) {
                flag = true;
            }
        }

        Assert.isTrue(flag, MessageCodes.ORDER_CHILD_ERROR);


        // 判断该套餐是否是该课程的

        // 封装下单用户 ID, 手机号
        Parent parent = parentService.getById(parentId);
        Assert.notNull(parent, MessageCodes.PARENT_IS_NOT_EXIST);
        order.setParentId(parent.getParentId())
                .setParentPhone(parent.getPhone())
                .setParentName(parent.getParentName())
                .setNickName(parent.getNickName());

        // 封装孩子的 ID, 姓名, 学号, 年级, 班级
        Child child = childService.getById(orderDTO.getChildId());
        Assert.notNull(child, MessageCodes.CHILD_IS_NOT_EXIST);
        order.setChildId(child.getChildId())
                .setChildName(child.getName())
                .setChildStudentNo(child.getStudentNo())
                .setChildGrade(child.getGrade())
                .setChildClass(child.getChildClass());

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);

        // 封装课程 ID, 名称, 简介, 学校名称, 封面
        log.info(orderDTO.getServiceId().toString());
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        order.setServiceName(course.getCourseName())
                .setServiceCover(course.getCoverUrl())
                .setIntroduction(course.getIntroduction())
                .setSchoolName(course.getSchoolName())
                .setSchoolId(course.getSchoolId());

        // 封装课程套餐的 价格, 开始时间, 结束时间

        order.setPrice(cp.getPrice())
                .setServiceId(cp.getCoursePackageId())
                .setServiceStartTime(cp.getStartTime())
                .setServiceEndTime(cp.getEndTime());

        // 01-07新增: 教师ID,教师名称,上课时间,下课时间
        order.setTeacherId(course.getTeacherId())
                .setTeacherName(course.getTeacherName())
                .setClassTime(cp.getClassTime())
                .setQuittingTime(cp.getQuittingTime());

        // 订单类型, 订单状态
        order.setOrderType(OrderTypeEnum.COURSE)
                .setOrderStatus(OrderStatusEnum.NOT_PAID);

        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTimestamp());
        System.out.println(order.getOrderId());

        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);
    }

    /**
     * 批量删除Order
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrders(Set<String> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.ORDER_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 获取单个Order
     *
     * @param id
     * @return
     */
    public OrderCmsVO getOrder(String id) {
        Order order = findById(id);
        return toOrderVO(order);
    }

    /**
     * 分页获取Order
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderCmsVO> findPage(MyOrderQueryDTO queryDTO, Page<Order> page) {

        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();
        // noinspection unchecked
        wrapper.eq(Order::getParentId, UserHelper.getUserId())
                .orderByDesc(Order::getCreateTime);
        if (queryDTO.getOrderStatus() != null) {
            wrapper.eq(Order::getOrderStatus, queryDTO.getOrderStatus());
        }

        // 分页查询
        return ConversionBeanUtils.conversionBean(page(page, wrapper), this::toOrderVO);
    }

    public WechatPayPackage payForOrder(PayForOrderDTO addDTO) {

        Integer userId = UserHelper.getUserId();
        Parent parent = parentService.getById(userId);
        Assert.notNull(parent, MessageCodes.PARENT_IS_NOT_EXIST);

        Order order = findById(addDTO.getOrderId());

        //生成预支付信息
        System.out.println(notifyUrl);
        // 正式上线需改为实际价格
        BigDecimal multiply = order.getPrice().multiply(BigDecimal.valueOf(100));
        int price = multiply.intValue();
        return weChatUtils.getWechatPayPackage(
                parent.getOpenId(), "hosting-order-pay", "hosting-order-pay", order.getOrderId(),
                String.valueOf(price), "JSAPI", notifyUrl);

    }

    /**
     * 支付成功后的调用此方法
     *
     * @param wxNotifyResultVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notify(WXNotifyResultVo wxNotifyResultVo) {
        log.info("WXNotifyResultVO: {}", wxNotifyResultVo.toString());
        log.info("==========支付成功，调用支付成功后的业务逻辑============");
        Order order = findById(wxNotifyResultVo.getOutTradeNo());
        if (order.getOrderStatus() == OrderStatusEnum.PAID) {
            return;
        }
        // 判断金额是否一致
        String totalFee = wxNotifyResultVo.getTotalFee();// 测试,支付1分钱,正式上线需要改成实际价格
        int price = order.getPrice().multiply(BigDecimal.valueOf(100)).intValue();
        String strPrice = String.valueOf(price);
        if (!strPrice.equals(totalFee)) {
            log.error("支付金额{} 与订单金额{} 不一致", totalFee, strPrice);
            throw new ValidationException(MessageCodes.ORDER_PAY_AMOUNT_ERROR);
        }
        // 修改当前订单状态
        order.setPayTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatusEnum.PAID);
        order.setPayment(PaymentEnum.WECHAT);
        order.setTransactionId(wxNotifyResultVo.getTransactionId());
        order.setUpdateTime(LocalDateTime.now());
        Assert.isTrue(updateById(order), MessageCodes.ORDER_STATUS_UPDATE_ERROR);
        log.info("订单编号: {} 修改状态为已支付", order.getOrderId());

        //修改课程/托管报名人数
        if (order.getOrderType() == OrderTypeEnum.HOSTING) {
            Hosting hosting = hostingService.getById(order.getServiceId());
            Hosting upHosting = new Hosting();
            upHosting.setHostingId(hosting.getHostingId());
            upHosting.setApplicantsNumber(hosting.getApplicantsNumber() + 1);
            Assert.isTrue(hostingService.updateById(upHosting), MessageCodes.UPDATE_HOSTING_APPLICATANTS_NUMBER_ERROR);
            log.info("托管修改报名人数:", upHosting);
        }

        //修改课程报名人数
        if (order.getOrderType() == OrderTypeEnum.COURSE) {
            CoursePackage coursePackage = coursePackageService.getById(order.getServiceId());
            CoursePackage upCoursePackage = new CoursePackage();
            upCoursePackage.setCoursePackageId(order.getServiceId());
            upCoursePackage.setApplicantsNumber(coursePackage.getApplicantsNumber() + 1);
            Assert.isTrue(coursePackageService.updateById(upCoursePackage), MessageCodes.UPDATE_COURSEPACKAGE_APPLICATANTS_NUMBER_ERROR);
            log.info("课程修改报名人数", upCoursePackage);
        }
    }


    //推荐购买
    public OrderCmsVO recommedBuy(RecommedBuyDTO recommedBuyDTO) {
        Order order = new Order();


        // 封装课程数据
        Recommend recommed = recommendService.findBySeverId(recommedBuyDTO.getServiceId());
        //獲取家长信息
        Parent parent = parentService.getById(recommedBuyDTO.getParentId());


        //设置关于托管班的信息
        order.setServiceName(recommed.getServiceName());
        order.setServiceCover(recommed.getServiceCoverUrl());
        order.setIntroduction(recommed.getServiceIntroduction());
        order.setServiceId(recommed.getServiceId());
        order.setSchoolId(recommed.getSchoolId());
        order.setSchoolName(recommed.getSchoolName());
        if (recommed.getServiceType().getValue() == 0) {
            order.setOrderType(OrderTypeEnum.COURSE);
        }
        if (recommed.getServiceType().getValue() == 1) {
            order.setOrderType(OrderTypeEnum.HOSTING);
        }


        //设置关于家长的信息
        order.setParentId(parent.getParentId());
        order.setParentPhone(parent.getPhone());
        order.setParentName(parent.getParentName());
        order.setNickName(parent.getNickName());


        //开始时间与结束时间
        order.setServiceStartTime(recommed.getStartTime());
        order.setServiceEndTime(recommed.getEndTime());

        //设置价格
        BigDecimal severPrice = recommed.getServicePrice();
        order.setPrice(severPrice);

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);


        // 订单类型, 订单状态
        order.setOrderStatus(OrderStatusEnum.NOT_PAID);

        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTimestamp());
        System.out.println(order.getOrderId());

        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);
    }

    public List<Order> findByOrderId(Integer id) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getParentId, id).orderByDesc(Order::getCreateTime);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        return orders;
    }

    public OrderCmsVO hostingBuy(HostingBuyDTO hostingBuyDTO) {

        Order order = new Order();


        // 封装课程数据
        HostingWebVO hostingWebVO = hostingService.findById(hostingBuyDTO.getHostingId());
        //獲取家长信息
        Parent parent = parentService.getById(hostingBuyDTO.getParentId());


        //设置关于托管班的信息
        order.setServiceName(hostingWebVO.getName());
        order.setServiceCover(hostingWebVO.getCoverUrl());
        order.setIntroduction(hostingWebVO.getIntroduction());
        order.setServiceId(hostingWebVO.getHostingId());
        order.setSchoolId(hostingWebVO.getSchoolId());
        order.setSchoolName(hostingWebVO.getSchoolName());
        order.setOrderType(OrderTypeEnum.HOSTING);


        //设置关于家长的信息
        order.setParentId(parent.getParentId());
        order.setParentPhone(parent.getPhone());
        order.setParentName(parent.getParentName());
        order.setNickName(parent.getNickName());


        //开始时间与结束时间
        order.setServiceStartTime(hostingWebVO.getStartTime());
        order.setServiceEndTime(hostingWebVO.getEndTime());

        //设置价格
        BigDecimal severPrice = hostingWebVO.getPrice();
        order.setPrice(severPrice);

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);


        // 订单类型, 订单状态
        order.setOrderStatus(OrderStatusEnum.NOT_PAID);

        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTimestamp());
        System.out.println(order.getOrderId());

        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);
    }

    public boolean pay(String id) {
        return orderMapper.pay(id);
    }

    public OrderCmsVO courseBuy(CourseBuyDTO courseBuyDTO) {
        Order order = new Order();

        // 封装课程数据
        CourseWebVO course = courseService.getCourse(courseBuyDTO.getCourseId());
        //獲取家长信息
        Parent parent = parentService.getById(courseBuyDTO.getParentId());

        //设置关于托管班的信息
        order.setServiceName(course.getCourseName());
        order.setServiceCover(course.getCoverUrl());
        order.setIntroduction(course.getIntroduction());
        order.setServiceId(course.getCourseId());
        order.setSchoolId(course.getSchoolId());
        order.setSchoolName(course.getSchoolName());
        order.setOrderType(OrderTypeEnum.COURSE);
        //设置关于家长的信息
        order.setParentId(parent.getParentId());
        order.setParentPhone(parent.getPhone());
        order.setParentName(parent.getParentName());
        order.setNickName(parent.getNickName());
        //开始时间与结束时间
        order.setServiceStartTime(course.getStartTime());
        order.setServiceEndTime(course.getEndTime());
        //设置价格
        BigDecimal severPrice = course.getPrice();
        order.setPrice(severPrice);

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);

        // 订单类型, 订单状态
        order.setOrderStatus(OrderStatusEnum.NOT_PAID);
        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTimestamp());
        System.out.println(order.getOrderId());

        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);

    }
}
