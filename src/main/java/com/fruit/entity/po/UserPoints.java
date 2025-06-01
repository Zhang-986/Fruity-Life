package com.fruit.entity.po;


import lombok.Data;

import java.io.Serializable;

import java.util.Date;

@Data
/**
 * @TableName user_points
 */
public class UserPoints implements Serializable {

    /**
     *
     */

    private Long userId;
    /**
     *
     */

    private Long points;
    /**
     *
     */

    private Date createdAt;
    /**
     *
     */

    private Date updatedAt;


}
