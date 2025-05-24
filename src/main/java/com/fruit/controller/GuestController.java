package com.fruit.controller;

import com.fruit.entity.GuestSessions;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "GuestController", description = "用户注册控制器")
@RequestMapping("/user")
public class GuestController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final IGuestSessions iGuestSessions;

    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public R<String> register(@RequestBody GuestSessions guestSessions) {
        log.info("注册信息: {}", guestSessions);
        iGuestSessions.register(guestSessions);
        return R.ok("注册成功");
    }
    @Operation(summary = "获取验证码", description = "获取验证码接口")
    @GetMapping("/getCode")
    public R<String> getCode(String email){
        log.info("获取验证码");
        return iGuestSessions.getCode(email);
    }

}