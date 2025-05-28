package com.fruit.entity.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * 存储游客临时会话信息，用于个性化推荐和BMI计算
 * @TableName guest_sessions
 */
public class GuestSessionsDTO implements Serializable {

    /**
     * 游客会话ID，由前端或后端生成并维护，用于标识匿名用户会话
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 验证码
     */
    private String code;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别：MALE(男性), FEMALE(女性), UNKNOWN(未知)
     */

    private String gender;
    /**
     * 身高，单位：厘米
     */

    private BigDecimal heightCm;
    /**
     * 体重，单位：公斤
     */

    private BigDecimal weightKg;
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

    private BigDecimal bmiValue;
    /**
     * 记录创建时间，会话开始时间
     */

    private Date createdAt;
    /**
     * 记录最后更新时间，会话数据更新时间
     */

    private Date updatedAt;


}
