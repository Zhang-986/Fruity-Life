package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailUtil;
import com.fruit.entity.GuestSessions;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@RequiredArgsConstructor
@Service
public class GuestSessionsImpl implements IGuestSessions, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final RedisTemplate<String,String> template;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void register(GuestSessions guestSessions) {
        // 1. 校验
        boolean empty = BeanUtil.isEmpty(guestSessions);
        if(empty){
            throw new RuntimeException("注册信息不能为空");
        }
        // 2.

    }

    @Override
    public R<String> getCode(String email) {
        // 1. 生成四位数验证码
        String code = String.valueOf((int) (Math.random() * 9000) + 1000);
        // 2. 存入redis
        // 设置5分钟过期时间
        redisTemplate.opsForValue().set("code", code, 60 * 5L);
        // 3. 发送邮箱处理
        if(BeanUtil.isEmpty(email)){
            return R.error("邮箱不能为空");
        }
        if(!email.contains("@")) {
            return R.error("邮箱格式不正确");
        }
        if(!email.contains(".")) {
            return R.error("邮箱格式不正确");
        }
        if(email.length() < 5) {
            return R.error("邮箱格式不正确");
        }
        // 4. 发送验证码

        return R.ok("验证码已发送至邮箱，请注意查收");
    }
}