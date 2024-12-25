package com.jojo.model;

import com.jojo.constant.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结果对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("项目统一响应结果对象")
public class Result<T> implements Serializable {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("附加数据")
    private Map<Object,Object> other = new HashMap<>();

    public Result(int code, String message,T data) {
        this.code = code;
        this.data = data;
        this.msg = message;
    }

    public static <T> Result<T> success(T data){
        return new Result<>(StatusEnum.SUCCESS.getCode(),StatusEnum.SUCCESS.getDesc(),data);
    }
    public static <T> Result<T> success(){
        return success(null);
    }


    public static <T> Result<T> fail(Integer code,String msg){
        return new Result<>(code,msg,null);
    }

    public static <T> Result<T> fail(StatusEnum statusEnum){
        return fail(statusEnum.getCode(),statusEnum.getDesc());
    }

    public static <T> Result<T> unauthorized(){
        return fail(StatusEnum.UN_AUTHORIZATION);
    }

    public static <T> Result<T> forbidden(Integer code,String msg){
        return fail(StatusEnum.ACCESS_DENY_FAIL);
    }

    /**
     * 处理用户的操作
     * @param flag
     * @return
     */
    public static Result<?> handle(boolean flag) {
        return flag ? Result.success() : Result.fail(StatusEnum.OPERATION_FAIL);
    }
}
