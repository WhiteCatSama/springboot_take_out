package com.whitecatsama.delicious_take_out.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
* 服务返回结果类
* */
@Data
public class Result{

    private Integer code;

    private String msg;

    private Object data;

    private Map map = new HashMap();

    public Result() {
    }

    public Result(Integer code, String msg, Object data, Map map) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.map = map;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data,String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}