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

    //用户相关
    String USER_NOT_EXIST = "user.not.exist";//用户不存在

    //加密解密相关
    String RSAUtil_DECRYPT_ERROR = "rsautil.decrypt.error";//解密失败

    //Notice先关
    String NOTICE_UPDATE_ERROR = "notice update fail";//公告更新失败

    //轮播图相关
    String BANNER_UPDATE_ERROR = "banner.update.fail";//轮播图更新失败
}
