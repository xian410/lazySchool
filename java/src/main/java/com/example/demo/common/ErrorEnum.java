package com.example.demo.common;

import lombok.Getter;

/**
 * 通用错误枚举
 * @author zuozhiwei
 */
public enum ErrorEnum {
    /**
     * 内部错误
     */
    INNER_ERROR(2, "系统发生异常，请联系管理员"),
    /**
     * 无访问权限
     */
    INVALID_ACCESS(401, "无访问权限");

    /**
     * 错误码
     */
    @Getter
    private final int code;
    /**
     * 错误信息
     */
    @Getter
    private final String msg;

    /**
     * 构造函数
     * @param code
     * @param msg
     */
    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
