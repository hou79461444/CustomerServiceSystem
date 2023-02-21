package com.customerService.exception;


// 自定义项目业务级异常类型
public class BusinessException extends RuntimeException {

    private Integer code;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
}
