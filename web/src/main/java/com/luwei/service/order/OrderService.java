package com.luwei.service.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.common.util.OrderIdUtils;
import com.luwei.model.child.Child;
import com.luwei.model.course.Course;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.order.Order;
import com.luwei.model.order.OrderMapper;
import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import com.luwei.model.order.pojo.cms.OrderVO;
import com.luwei.model.order.pojo.web.ConfirmOrderDTO;
import com.luwei.model.order.pojo.web.HostingOrderDTO;
import com.luwei.model.order.pojo.web.MyOrderQueryDTO;
import com.luwei.model.order.pojo.web.PayForOrderDTO;
import com.luwei.model.parent.Parent;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.child.ChildService;
import com.luwei.service.course.CourseService;
import com.luwei.service.coursepackage.CoursePackageService;
import com.luwei.service.hosting.HostingService;
import com.luwei.service.parent.ParentService;
import com.luwei.service.parentchild.ParentChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Resource
    private ParentService parentService;

    @Resource
    private ChildService childService;

    @Resource
    private CourseService courseService;

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private ParentChildService parentChildService;
    @Resource
    private HostingService hostingService;



    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Order findById(Long id) {
        // 若此id已被逻辑删除,也会返回null
        Order order = getById(id);
        // TODO 修改MessageCodes
        Assert.notNull(order, MessageCodes.ORDER_IS_NOT_EXIST);
        return order;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param order
     * @return
     */
    private OrderVO toOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    /**
     * 确认下单/立即购买(托管)
     *
     * @param
     * @return
     */
    @Transactional
    public OrderVO hostingOrder(HostingOrderDTO hostingOrderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(hostingOrderDTO, order);

        // 封装课程数据
        Hosting hosting = hostingService.getById(order.getServiceId());

        Integer parentId = UserHelper.getUserId();
        Parent parent = parentService.getById(parentId);
        Child child = childService.getById(hostingOrderDTO.getChildId());

        //判断所选时间是否有效
/*        if (hosting.getStartTime().compareTo(hostingOrderDTO.getStartTime())>0 || hosting.getEndTime().compareTo(hostingOrderDTO.getStartTime())<0){
            Assert.isTrue(true, MessageCodes.ORDER_TIME_ERROR);
        }*/


        //判断孩子是不是家长的孩子



        //设置关于托管班的信息
        order.setServiceName(hosting.getName());
        order.setServiceCover(hosting.getCoverUrl());
        order.setIntroduction(hosting.getIntroduction());
        order.setServiceId(hosting.getHostingId());
        order.setSchoolName(hosting.getSchoolName());


        //设置关于家长的信息
        order.setParentId(parent.getParentId());
        order.setParentPhone(parent.getPhone());

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
        order.setPrice(getprice(hostingOrderDTO.getStartTime(),hostingOrderDTO.getEndTime()));

        // 支付方式 - 待定
        //order.setPayment(PaymentEnum.WECHAT);

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

    //计算价格
    private static BigDecimal getprice(LocalDateTime stertTime , LocalDateTime endTime){

        return new BigDecimal(0);
    }

   /*
    *
    * 确认下单/立即购买
    * */
    @Transactional
    public OrderVO confirmOrder(ConfirmOrderDTO orderDTO) {
        Order order = new Order();

        // TODO 判断该家长是否绑定此学生
        // TODO 判断该套餐是否是该课程的

        // 封装下单用户 ID, 手机号
        Parent parent = parentService.getById(UserHelper.getUserId());
        Assert.notNull(parent, MessageCodes.PARENT_IS_NOT_EXIST);
        order.setParentId(parent.getParentId())
                .setParentPhone(parent.getPhone());

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
        Course course = courseService.getById(orderDTO.getServiceId());
        log.info(orderDTO.getServiceId().toString());
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        order.setServiceId(course.getCourseId())
                .setServiceName(course.getCourseName())
                .setServiceCover(course.getCoverUrl())
                .setIntroduction(course.getIntroduction())
                .setSchoolName(course.getSchoolName());

        // 封装课程套餐的 价格, 开始时间, 结束时间
        CoursePackage cp = coursePackageService.getById(orderDTO.getPackageId());
        order.setPrice(cp.getPrice())
                .setServiceStartTime(cp.getStartTime())
                .setServiceEndTime(cp.getEndTime());

        // 订单类型, 订单状态
        order.setOrderType(OrderTypeEnum.COURSE)
                .setOrderStatus(OrderStatusEnum.NOT_PAID);

        // 创建时间 更新时间 逻辑删除(插件处理)
        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time).setCreateTime(time);

        // 生成订单编号
        order.setOrderId(OrderIdUtils.getOrderIdByTime());
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
    @Transactional
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
    public OrderVO getOrder(Long id) {
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
    @Transactional
    public IPage<OrderVO> findPage(MyOrderQueryDTO queryDTO, Page<Order> page) {

        // 先分页查询
        Order order = new Order();
        QueryWrapper<Order> wrapper = new QueryWrapper<>(order);
        if (queryDTO.getOrderStatus() != null) {
            wrapper.eq("order_status", queryDTO.getOrderStatus().getValue());
        }
        IPage<Order> orderIPage = baseMapper.selectPage(page, wrapper);

        // 处理订单: 是否已过期 是否已完成
        orderIPage.setRecords(orderIPage.getRecords().stream().map(this::updateStatus).collect(Collectors.toList()));

        // 返回结果
        return ConversionBeanUtils.conversionBean(orderIPage, this::toOrderVO);
    }

    /**
     * 私有方法 处理订单,判断订单是否已过期或已完成
     * @param order
     * @return
     */
    private Order updateStatus(Order order) {
        if (order.getOrderStatus() == OrderStatusEnum.NOT_PAID) {
            LocalDateTime serviceEndTime = order.getServiceEndTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.compareTo(serviceEndTime) > 0) {
                order.setOrderStatus(OrderStatusEnum.OVERDUE);
                updateById(order);
            }
        }
        if (order.getOrderStatus() == OrderStatusEnum.PAID) {
            LocalDateTime serviceEndTime = order.getServiceEndTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.compareTo(serviceEndTime) > 0) {
                order.setOrderStatus(OrderStatusEnum.COMPLETED);
                updateById(order);
            }
        }
        return order;
    }

    public OrderVO payForOrder(@Valid PayForOrderDTO addDTO) {

        // TODO 立即支付接口 未完成

        return null;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().getDayOfWeek());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.compareTo(now));
        System.out.println(LocalDateTime.now().minusMonths(1).getDayOfWeek());


    }
}
