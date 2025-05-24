package com.fruit.result;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果封装类
 * @param <T> 返回数据类型
 */
@Data
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // 状态码
    private Integer code;

    // 返回消息
    private String msg;

    // 返回数据
    private T data;

    // 扩展数据（可选）
    private Map<String, Object> extra;

    // 成功状态码
    public static final int SUCCESS_CODE = 200;
    // 失败状态码
    public static final int ERROR_CODE = 500;

    // 私有构造方法
    private R() {}

    /**
     * 成功返回（无数据）
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * 成功返回（带数据）
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(SUCCESS_CODE);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    /**
     * 失败返回（默认消息）
     */
    public static <T> R<T> error() {
        return error("操作失败");
    }

    /**
     * 失败返回（自定义消息）
     */
    public static <T> R<T> error(String msg) {
        return error(ERROR_CODE, msg);
    }

    /**
     * 失败返回（自定义状态码和消息）
     */
    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 设置返回消息（链式调用）
     */
    public R<T> message(String msg) {
        this.setMsg(msg);
        return this;
    }

    /**
     * 设置状态码（链式调用）
     */
    public R<T> code(int code) {
        this.setCode(code);
        return this;
    }

    /**
     * 添加扩展数据（链式调用）
     */
    public R<T> put(String key, Object value) {
        if (this.extra == null) {
            this.extra = new HashMap<>();
        }
        this.extra.put(key, value);
        return this;
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code != null && this.code == SUCCESS_CODE;
    }
}