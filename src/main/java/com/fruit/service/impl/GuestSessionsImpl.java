package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fruit.entity.GuestSessions;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public R<String> getCode() {
        // 1. 生成四位数验证码
        String code = String.valueOf((int) (Math.random() * 9000) + 1000);
        // 2. 存入redis
        redisTemplate.opsForValue().set("code", code);
        // 3.
        return null;
    }
}