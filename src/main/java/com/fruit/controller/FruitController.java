package com.fruit.controller;

import com.fruit.entity.Fruits;
import com.fruit.result.R;
import com.fruit.service.IFruit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}