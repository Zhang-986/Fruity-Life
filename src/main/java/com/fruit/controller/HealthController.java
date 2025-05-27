package com.fruit.controller;

import com.fruit.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/27
 */
@RestController

public class HealthController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @GetMapping("/health")
    public R<String> healthCheck() {
        return R.ok("服务运行正常");
    }
}