package com.baidu.shop.base;

import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName BaseApiService
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/18
 * @Version V1.0
 **/
@Data
@Component
@Slf4j
public class BaseApiService<T> {
    public Result<T> setResultError(Integer code,String message){
        return setResult(code,message,null);
    }

    //返回错误 可以传message
    public Result<T> setResultError(String message){
        return setResult(HTTPStatus.ERROR,message,null);
    }

    //返回成功 可以传data值
    public Result<T> setResultSuccess(T data){
        return setResult(HTTPStatus.OK , HTTPStatus.OK + "" , data);
    }

    //返回成功 没有传data值
    public Result<T> setResultSuccess(){
        return setResult(HTTPStatus.OK , HTTPStatus.OK + "" , null);
    }

    //返回成功 没有data值
    public Result<T> setResultSuccess(String message){
        return setResult(HTTPStatus.OK , message , null);
    }

    //通用封装
    public Result<T> setResult(Integer code,String message,T data){
        log.info(String.format("{code : %s , message : %s , data : %s }",code,message, JSONUtil.toJsonString(data)));
        return new Result<T>(code,message,data);
    }
}
