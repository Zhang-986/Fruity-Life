package com.fruit.entity.po;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (Tasks)实体类
 *
 * @author makejava
 * @since 2025-06-02 12:45:10
 */
@Data
public class Tasks implements Serializable {
    @Serial
    private static final long serialVersionUID = 431101392770383621L;

    private Long id;

    private String title;

    private String description;

    private Long points;

    private Integer isActive;

    private Date createdAt;

    private Date updatedAt;


}

