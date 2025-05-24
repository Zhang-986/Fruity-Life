package com.fruit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */

@RestController
@Tag(name = "RegisterController", description = "用户注册控制器")
@RequestMapping("/register")
public class RegisterController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


}