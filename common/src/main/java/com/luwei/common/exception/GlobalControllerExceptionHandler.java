package com.luwei.common.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.security.auth.login.FailedLoginException;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Resource
    private ApplicationContext applicationContext;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Result handleBaseException(ValidationException e) {
        logger.error("", e);
        String desc = applicationContext.getMessage(e.getCode(), null, e.getMsg(), Locale.getDefault());
        if (StringUtils.isEmpty(desc)) {
            logger.info("can not find desc of collector:" + e.getCode());
            desc = e.getCode();
        }
        return new Result(e.getCode(), desc);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(Throwable e) {
        logger.error("", e);
        String code = MessageCodes.AUTH_PERMISSION;
        String message = applicationContext.getMessage(code, null, Locale.getDefault());
        return new Result(code, message);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(Throwable e) {
        logger.error("", e);
        String code = MessageCodes.AUTH_TOKEN;
        String message = applicationContext.getMessage(code, null, Locale.getDefault());
        return new Result(code, message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedLoginException.class)
    public Result handleFailedLoginException(FailedLoginException e) {
        logger.error("", e);
        String code = MessageCodes.AUTH_ACCOUNT_PASSWORD_WRONG;
        String message = applicationContext.getMessage(code, null, Locale.getDefault());
        return new Result(code, message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleBaseException(IllegalArgumentException e) {
        logger.error("", e);
        String code = MessageCodes.REQUEST_ARGUMENT;
        String message = e.getMessage();
        final String nullTarget = "Target must not be null";
        if (StringUtils.isEmpty(message) || Objects.equals(nullTarget, message)) {
            message = "请求参数错误";
        }
        String desc = applicationContext.getMessage(message, null, null, Locale.getDefault());
        if (desc != null) {
            code = message;
            message = desc;
        }
        return new Result(code, message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result handleBaseException(Throwable e) {
        logger.error("", e);
        String message = e.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "服务器内部错误";
        }
        return new Result(MessageCodes.INTERNAL_SERVER_ERROR, message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("", e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        if (StringUtils.isEmpty(message)) {
            message = "请求数据格式错误";
        }
        return new Result(MessageCodes.REQUEST_ARGUMENT, message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Result handleDataIntegrityViolationException(NullPointerException e) {
        logger.error("", e);
        return new Result(MessageCodes.REQUEST_ARGUMENT, "请求对象不存在");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("", e);
        String eMessage = e.getMessage();
        return new Result(MessageCodes.REQUEST_ARGUMENT, StringUtils.isEmpty(eMessage) ? "请求参数缺失" : eMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result handleRequestBindException(BindException e) {
        logger.error("error: {}", e);
        StringBuffer eMessageBuf = new StringBuffer();
        e.getBindingResult().getAllErrors().forEach(error -> {
            eMessageBuf.append(error.getDefaultMessage()).append(" ");
        });
        String eMessage = eMessageBuf.toString();
        return new Result(MessageCodes.REQUEST_ARGUMENT, StringUtils.isEmpty(eMessage) ? "请求参数缺失" : eMessage);
    }

}
