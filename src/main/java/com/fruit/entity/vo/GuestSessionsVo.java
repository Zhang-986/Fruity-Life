package com.fruit.entity.vo;


import lombok.Data;

import java.io.Serializable;

@Data

public class GuestSessionsVo implements Serializable {

    /**
     * 游客会话ID，由前端或后端生成并维护，用于标识匿名用户会话
     */

    private Long id;
    /**
     * 性别：MALE(男性), FEMALE(女性), UNKNOWN(未知)
     */
    private String gender;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 身高，单位：厘米
     */

    private Double heightCm;
    /**
     * 是否完成
     */
    private Boolean isCompleted;

    private Double weightKg;
    /**
     * 年龄
     */

    private Integer age;
    /**
     * 用户的邮箱地址，非必填项
     */

    private String email;
    /**
     * 计算出的BMI指数，保留一位小数
     */

    private Double bmiValue;
    /**
     *
     * 记录创建时间，会话开始时间
     */


}
