package com.jojo.constant;

/**
 * 业务响应状态码
 */
public enum StatusEnum {
    SUCCESS(200,"请求成功"),
    OPERATION_FAIL(-1,"操作失败"),
    SERVER_INNER_ERROR(9999,"服务器内部异常"),
    UN_AUTHORIZATION(401,"未授权"),
    ACCESS_DENY_FAIL(403,"权限不足"),
    NOT_FOUND(404,"页面找不到"),
    REALNAME_FAIL(410,"实名失败")
    ;
    private final Integer code;
    private final String desc;
    StatusEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
