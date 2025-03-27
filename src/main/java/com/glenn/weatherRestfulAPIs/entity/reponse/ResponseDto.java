package com.glenn.weatherRestfulAPIs.entity.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:41
 * @PackageName com.glenn.weatherRestfulAPIs.entity.reponse
 * @ClassName ResponseDto
 * @Description common response entity
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements Serializable {
    // 响应码
    private Integer code;
    // 提示信息
    private String msg;
    // 具体内容
    private T data;

    public ResponseDto(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
