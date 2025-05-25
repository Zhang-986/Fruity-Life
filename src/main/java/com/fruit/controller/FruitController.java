package com.fruit.controller;

import com.fruit.entity.Fruits;
import com.fruit.result.R;
import com.fruit.service.IFruit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@RestController
@Tag(name = "FruitController", description = "水果行为控制器")
@RequestMapping("/fruit")
@Slf4j
public class FruitController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private IFruit fruitService;

    /**
     * 添加水果
     */
    @Operation(summary = "添加水果", description = "添加新的水果信息到数据库")
    @PostMapping("/addFruit")
    public R addFruit(@RequestBody Fruits fruit) {
        log.info("添加水果: {}", fruit);
        fruitService.insert(fruit);
        return R.ok("添加水果成功");
    }

    /**
     * 删除水果
     */
    @DeleteMapping("/deleteFruit/{id}")
    @Operation(summary = "删除水果", description = "根据ID删除水果信息")
    public R deleteFruit(Long id) {
        log.info("删除水果: {}", id);
        // 这里可以添加删除水果的逻辑
        fruitService.deleteById(id);
        return R.ok("删除水果成功");
    }

    /**
     * 查找获取水果信息
     */
    @GetMapping("/getFruit/{id}")
    @Operation(summary = "获取水果信息", description = "根据ID获取水果的详细信息")
    public R<Fruits> getFruit(@PathVariable Long id) {
        log.info("获取水果信息 :{}", id);
        Fruits fruit = fruitService.getById(id);
        return R.ok(fruit);
    }
    /**
     * 修改水果信息
     */
    @PutMapping("/updateFruit")
    @Operation(summary = "修改水果信息", description = "根据ID修改水果的详细信息")
    public R<String> updateFruit(@RequestBody Fruits fruit) {
        log.info("修改水果信息: {}", fruit);
        // 这里可以添加修改水果的逻辑
        fruitService.update(fruit);
        return R.ok("修改水果信息成功");
    }


}