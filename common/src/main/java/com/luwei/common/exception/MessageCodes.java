package com.luwei.common.exception;

public interface MessageCodes {

    String AUTH_TOKEN = "auth.token.wrong";//token错误或已过期,请重新登录
    String AUTH_TOKEN_EMPTY = "auth.token.empty";//token为空
    String AUTH_ACCOUNT_PASSWORD_WRONG = "auth.account.password.wrong";//账号或密码错误
    String ACCOUNT_HAS_DISABLED = "account.has.disabled";//账号已被禁用
    String AUTH_PERMISSION = "auth.permission";//权限不足
    String REQUEST_ARGUMENT = "request.argument";//请求参数错误或者参数为空
    String INTERNAL_SERVER_ERROR = "server.internal";//未知错误
    String ACCOUNT_ALREADY_EXIST = "account.already.exist";//账号已经存在
    String NOT_ROOT_ONLY_CAN_EDIT_SELF_INFO = "not.root.only.can.edit.self.info";//非超管只能编辑自己的信息
    String NOT_ALLOW_ADD_LUWEI_EXCEPT_ROOT = "not.allow.add.luwei.except.root";//不允许添加luwei之外的超管

    //验证码相关
    String AUTH_PICCAPTCHA_WRONG = "auth.captcha.wrong";//验证码错误
    String AUTH_PICCAPTCHA_LOST = "auth.captcha.lost";//验证码已失效

    //管理员相关
    String MANAGER_NOT_EXIST = "manager.not.exist";//管理员不存在
    String ADMIN_CANNOT_DELETE = "admin.cannot.delete";//超管不可删除
    String ADMIN_CANNOT_DISABLED = "admin.cannot.disabled";//超管不可禁用
    String NOT_ALLOW_SUPERIOR_ROLE = "not.allow.superior.role";//不能给同级或上级设权限
    String ROOT_CANNOT_ADD = "root.cannot.add";//超管不可手动添加

    //用户相关
    String USER_NOT_EXIST = "user.not.exist";//用户不存在

    //加密解密相关
    String RSAUtil_DECRYPT_ERROR = "rsautil.decrypt.error";//解密失败

    //数据CRUD错误
    String DATA_DELETE_ERROR = "data.delete.error";//数据删除失败
    String DATA_UPDATE_ERROR = "data.update.error";//数据更新失败
    String DATA_IS_NOT_EXIST = "data.is.not.exist";//数据不存在
    String DATA_SAVE_ERROR = "data.save.error";//数据保存失败

    //Notice先关
    String NOTICE_UPDATE_ERROR = "notice.update.fail";//公告更新失败
    String NOTICE_DELETE_ERROR = "notice.delete.error";//公告删除失败
    String NOTICE_IS_NOT_EXIST = "notice.is.not.exist";//公告不存在
    String NOTICE_SAVE_ERROR = "notice.save.error";//公告保存失败

    //Banner相关
    String BANNER_DELETE_ERROR = "banner.delete.error";//轮播图删除失败
    String BANNER_UPDATE_ERROR = "banner.update.error";//轮播图更新失败
    String BANNER_IS_NOT_EXIST = "banner.is.not.exist";//轮播图不存在
    String BANNER_SAVE_ERROR = "banner.save.error";//轮播图保存失败

    //孩子相关
    String CHILD_IS_NOT_EXIST = "child.is.not.exist";//孩子不存在
    String CHILD_UPDATE_ERROR = "child.is.update.error"; //孩子更新错误
    String CHILD_DELETE_ERROR = "child.is.delete.error"; //孩子删除错误
    String CHILD_IMPORT_FROM_EXCEL_ERROR = "child.import.is.excel.error"; //孩子excel导入错误
    String CHILD_BINDING_ERROR = "child.binding.error"; //孩子绑定错误

    //家长相关
    String PARENT_UPDATE_ERROR = "parent.is.update.error";//家长更新失败
    String PARENT_IS_NOT_EXIST = "家长不存在";//根据id查找家长失败
    String PARENT_DELETE_ERROR = "parent.delete。error";//家长删除失败

    //Course相关
    String COURSE_IS_NOT_EXIST = "course.is.not.exist";//课程不存在
    String COURSE_SAVE_ERROR = "course.save.error";//课程保存失败
    String COURSE_DELETE_ERROR = "course.delete.error";//课程删除失败
    String COURSE_UPDATE_ERROR = "course.update.error";//课程更新失败
    String COURSE_PACKAGE_SAVE_ERROR = "course.package.save.error";//课程套餐保存失败
    String COURSE_PACKAGE_UPDATE_ERROR = "course.package.update.error";//课程套餐更新失败
    String CLASS_TIME_ERROR = "class.time.error";//上课时间不能大于下课时间
    String PACKAGE_END_TIME_ERROR = "package.end.time.error";//套餐结束时间不能小于当前时间
    String UPDATE_COURSEPACKAGE_APPLICATANTS_NUMBER_ERROR = "update.coursepackage.applicatant.number.error";//更新课程套餐报名人数

    //老师相关
    String TEACHER_IS_NOT_EXIST = "teacher.is.not.exist";//老师不存在
    String TEACHER_SAVE_ERROR = "teacher.save.error";//老师不存在
    String TEACHER_DELETE_ERROR = "teacher.delete.error";//老师删除失败
    String TEACHER_IS_UPDATE_ERROR = "teacher.is.update.error";//老师删除失败
    String TEACHER_IMPORT_FROM_EXCEL_ERROR = "teacher.import.from.excel.error";//老师删除失败
    String TEACHER_UPDATE_ERROR = "teacher.update.error";//老师更新失败
    String TEACHER_HAS_BINDING = "teacher.has.binding";//老师已经被绑定
    String TEACHER_BINDING_ERROR = "teacher.binding.error";//老师绑定失败
    String PHONE_IS_INVALID = "phone.is.invalid";//电话号码不可用

    //与学校相关
    String SCHOOL_IS_NOT_EXIST = "school.is.not.exist";//学校不存在
    String SCHOOL_DELETE_ERROR = "school.delete.error";//学校删除失败
    String SCHOOL_SAVE_ERROR = "school.save.error";//学校保存失败
    String SCHOOL_CANNOT_DELETE = "school.cannot.delete";//已有老师绑定此学校.不可删除

    //Order相关
    String ORDER_IS_NOT_EXIST = "order.is.not.exist";//订单不存在
    String ORDER_SAVE_ERROR = "order.save.error";//订单保存失败
    String ORDER_DELETE_ERROR = "order.delete.error";//订单删除失败
    String ORDER_TIME_ERROR = "order.time.error";//订单的时间错误
    String ORDER_CHILD_ERROR = "order.child.error";//孩子不是该家长的孩子
    String ORDER_STATUS_UPDATE_ERROR = "order.status.update.error";//订单状态更新失败
    String DATE_IS_TO_LONG = "data.is.to.long";//所选日期过长

    //WeChat相关
    String WECHAT_AUTHORIZE_FAIL = "wechat.authorize.fail";//微信授权失败;
    String MINIUSER_SAVE_ERROR = "miniuser.save.fail";//小程序用户保存失败;

    //excel表相关
    String EXCEL_HAS_ERROR_DATA = "excel.has.error.data";//excel表数据错误

    //托管相关
    String HOSTING_IS_NOT_EXIST = "hosting.is.not.exit";//托管班不存在
    String HOSTING_SAVE_ERROR = "hosting.is.save.error";//托管班保存失败
    String HOSTING_DELETE_ERROR = "hosting.is.delete.error";//托管班保存失败
    String HOSTING_IS_UPDATE_ERROR = "hosting.is.update.error";//托管班保存失败
    String HOSTING_END_TIME_ERROR = "hosting.end.time.error";//托管结束时间不能小于当前日期
    String APPLICANTS_NUMBER_ENOUGH = "applicant.number.enough";//报名人数已经到达最大人数
    String UPDATE_HOSTING_APPLICATANTS_NUMBER_ERROR = "update.hosting.applicatants.number.error";//更新托管报名人数失败

    //推荐相关
    String RECOMMEND_IS_NOT_EXIST = "recommend.is.not.exist";//推荐不存在
    String RECOMMEND_WEIGHT_UPDATE_ERROR = "recommend.weight.update.exist";//推荐不存在

    //推荐服务相关
    String RECOMMEND_DELETE_ERROR = "recommend.delete.error";//推荐删除失败
    String RECOMMEND_ERROR = "recommend.error";//课程未上架不可推荐

    String ROOT_CANNOT_DISABLED = "root.cannot.disabled";//超管不可禁用
    String ROOT_CANNOT_DELETE = "root.cannot.delete";//超管不可删除
    String COURSE_PACKAGE_IS_DISPLAY = "course.package.is.display";//课程套餐已上架不可修改
    String MINIUSER_IS_NOT_EXIST = "miniuser.is.not.exist";//小程序用户不存在

    String ORDER_PAY_AMOUNT_ERROR = "order.pay.amount.error";//订单支付金额不一致
    String MANAGER_BINDING_SCHOOL_ERROR = "manager.binding.school.error";//管理员绑定学校失败
    String MANAGER_NOT_BINDING_SCHOOL = "manager.not.binding.school";//管理员没有绑定学校无法添加公告

    String ROOT_CANNOT_RESET = "root.cannot.reset";//在此不能重置ROOT密码

    //机构相关
    String INSTITUTION_SAVE_ERROR = "institution.save.error";//机构保存失败
    String INSTITUTION_DELETE_ERROR = "institution.delete.error";//机构删除失败
    String INSTITUTION_UPDATE_ERROR = "institution.update.error";//机构更新失败
    String INSTITUTION_IS_NOT_EXIST = "institution.is.not.exist";//机构不能为空

    //校验相关
    String INVALID_PHONE_NUMBER = "invalid.phone.number";//无效的电话号码


    String USERNAME_OR_PASSWORD_ERROR = "账号或者密码错误";//账号或者密码错误
}
