package com.fruit.entity.po;


import java.io.Serializable;

import java.util.Date;

import lombok.Data;

@Data
/**
 *
 * @TableName user_point_behavior
 *
 */
public class UserPointBehavior implements Serializable {

    /**
     *
     */

    private Long id;
    /**
     *
     */

    private Long userId;
    /**
     * 行为类型：sign_in/daily_checkin/share_content等
     */

    private String behaviorType;
    /**
     * 本次行为获得的积分
     */

    private Long pointsChange;
    /**
     * 行为后的积分余额
     */

    private Long pointsBalance;
    /**
     *
     */

    private Date createdAt;


}
