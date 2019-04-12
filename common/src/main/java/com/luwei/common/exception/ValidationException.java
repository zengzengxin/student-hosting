package com.luwei.common.exception;

/**
 * 正常业务层面验证数据或服务时，抛出的的ValidationException会被转换成Result返回
 *
 * @author luwei
 */
public class ValidationException extends RuntimeException {

    private String code;
    private String msg;
    private Object[] args;

    public ValidationException(String code) {
        this.code = code;
    }

    public ValidationException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMsg() {
        return msg;
    }

}
