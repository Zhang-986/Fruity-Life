package com.fruit.controller;

import com.fruit.entity.dto.PointsDTO;
import com.fruit.result.R;
import com.fruit.service.IPointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/6/1
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "PointsController", description = "用户注册控制器")
@RequestMapping("/points")
public class PointsController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final IPointsService pointsService;

    @GetMapping("/getPoints")
    @Operation(summary = "获取积分", description = "获取用户积分接口")
    public R<Long> getPoints() {
        log.info("获取积分");
        return R.ok(pointsService.getPoints());
    }

    @Operation(summary = "添加积分", description = "添加用户积分接口")
    @PostMapping("/addPoints")
    public R<Long> addPoints(@RequestBody PointsDTO pointsDTO) {
        Long points = pointsService.addPoints(pointsDTO);
        return R.ok(points);
    }


}