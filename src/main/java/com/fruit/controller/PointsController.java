package com.fruit.controller;

import com.fruit.result.R;
import com.fruit.service.IPoints;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/6/1
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "GuestController", description = "用户注册控制器")
@RequestMapping("/points")
public class PointsController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final IPoints pointsService;

    @GetMapping("/getPoints")
    @Operation(summary = "获取积分", description = "获取用户积分接口")
    public R<Long> getPoints() {
        log.info("获取积分");
        return R.ok(pointsService.getPoints());
    }

}