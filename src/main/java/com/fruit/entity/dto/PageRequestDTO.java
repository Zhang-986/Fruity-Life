package com.fruit.entity.dto;

import lombok.Data;

/**
 * 分页查询请求参数（接收前端传递的分页参数）
 */
@Data
public class PageRequestDTO {
    private Integer pageNum = 1;    // 当前页码，默认第1页
    private Integer pageSize = 10;  // 每页条数，默认10条

    // 示例：校验参数合法性
    public void checkParam() {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 10; // 限制每页最多100条
        }
    }
}