package com.fruit.controller;

import com.fruit.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/31
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Statistics", description = "数据量统计控制器")
@RequestMapping("/statistics")
public class StatisticsController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final RedisTemplate<String, String> template;

    @Operation(summary = "获取用户数量", description = "获取用户数量接口")
    @GetMapping("/getUserCount")
    public R<Long> getUserCount() {
        log.info("获取用户数量");
        String key = "statistics:guest:sessions:register:count";
        String s = template.opsForValue().get(key);
        if (s != null) {
            return R.ok(Long.valueOf(s));
        }
        return R.ok(0L);
    }

    @Operation(summary = "获取水果数量", description = "获取水果数量接口")
    @GetMapping("/getFruitCount")
    public R<Long> getFruitCount() {
        log.info("获取水果数量");
        String key = "statistics:fruit:count";
        String s = template.opsForValue().get(key);
        if (s != null) {
            return R.ok(Long.valueOf(s));
        }
        return R.ok(0L);
    }

    @Operation(summary = "获取特定水果数量", description = "获取特定水果数量接口")
    @GetMapping("/getFruitCountById/{id}")
    public R<Long> getFruitCountById(@PathVariable Long id) {
        log.info("获取特定水果数量: {}", id);
        String key = "statistics:fruit:" + id + ":count";
        String s = template.opsForValue().get(key);
        if (s != null) {
            return R.ok(Long.valueOf(s));
        }
        return R.ok(0L);
    }
    @Operation(summary = "获取任务数量", description = "获取任务数量接口")
    @GetMapping("/getTaskCount")
    public R<Long> getTaskCount() {
        log.info("获取任务数量");
        String key = "statistics:task:count";
        String s = template.opsForValue().get(key);
        if (s != null) {
            return R.ok(Long.valueOf(s));
        }
        return R.ok(0L);
    }
}