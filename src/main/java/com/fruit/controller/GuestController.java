package com.fruit.controller;

import com.fruit.entity.dto.GuestSessionsDTO;
import com.fruit.entity.po.GuestSessions;
import com.fruit.entity.vo.GuestSessionsVo;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import com.fruit.utils.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, String> template;

    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public R<String> register(@RequestBody GuestSessionsDTO guestSessions) {
        log.info("注册信息: {}", guestSessions);
        return iGuestSessions.register(guestSessions);

    }

    @Operation(summary = "获取验证码", description = "获取验证码接口")
    @GetMapping("/getCode")
    public R<String> getCode(String email) {
        log.info("获取验证码");
        return iGuestSessions.getCode(email);
    }

    @Operation(summary = "登入接口", description = "用户登入接口")
    @PostMapping("/login")
    public R<String> login(@RequestBody GuestSessionsDTO guestSessions) {
        log.info("登入信息: {}", guestSessions);
        return iGuestSessions.login(guestSessions);
    }

    @Operation(summary = "验证邮箱", description = "验证邮箱接口")
    @GetMapping("/verifyEmail")
    public R<Boolean> verifyEmail(String email) {
        log.info("验证邮箱: {}", email);
        return iGuestSessions.verifyEmail(email);
    }

    @Operation(summary = "处理密码", description = "处理密码接口")
    @PostMapping("/handlePassword")
    public R<String> handlePassword(@RequestBody GuestSessionsDTO guestSessions) {
        log.info("处理密码信息: {}", guestSessions);
        // 这里可以添加处理密码的逻辑
        return iGuestSessions.handlePassword(guestSessions);
    }

    @Operation(summary = "完善个人资料", description = "完善个人资料接口")
    @PostMapping("/completeProfile")
    public R<String> completeProfile(@RequestBody GuestSessionsDTO guestSessions) {
        log.info("完善个人资料信息: {}", guestSessions);
        // 这里可以添加完善个人资料的逻辑
        return iGuestSessions.completeProfile(guestSessions);
    }

    @Operation(summary = "获取用户是否完善信息", description = "获取用户是否完善信息接口")
    @GetMapping("/isCompleted")
    public R<String> isCompleted() {
        // 这里可以添加获取用户是否完善信息的逻辑
        Long userId = UserContext.getUserId();
        String flag = template.opsForValue().get("guest:sessions:completed:" + userId);
        if (flag.equals("false")) {
            return R.ok(flag);
        }
        return R.ok(flag);
    }

    @Operation(summary = "获取用户信息", description = "获取用户信息接口")
    @GetMapping("/getUserInfo")
    public R<GuestSessionsVo> getUserInfo() {
        Long userId = UserContext.getUserId();
        return iGuestSessions.getEntityById(userId);
    }
    @Operation(summary = "获取管理员账号" ,description = "管理员处理")
    @GetMapping("/getAdmin/{email}")
    public R<String> getAdmin(@PathVariable String email) {
        String e =  template.opsForValue().get("zzk:admin");
        if(e.equals(email)){
            return R.ok("675314");
        }
        return R.ok();
    }
}