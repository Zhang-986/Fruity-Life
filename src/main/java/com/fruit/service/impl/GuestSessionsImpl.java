package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailUtil;
import com.fruit.entity.po.GuestSessions;
import com.fruit.entity.dto.GuestSessionsDTO;
import com.fruit.mapper.GuestSessionsMapper;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@RequiredArgsConstructor
@Service
public class GuestSessionsImpl implements IGuestSessions, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final GuestSessionsMapper guestSessionsMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public R<String> register(GuestSessionsDTO guestSessions) {
        // 1. 校验
        boolean empty = BeanUtil.isEmpty(guestSessions);
        if (empty) {
            throw new RuntimeException("注册信息不能为空");
        }
        // 2. 校验邮箱格式
        String email = guestSessions.getEmail();
        if (BeanUtil.isEmpty(email) || !email.contains("@") || !email.contains(".") || email.length() < 5) {
            throw new RuntimeException("邮箱格式不正确");
        }
        // 3. 校验验证码
        String code = redisTemplate.opsForValue().get("code:" + email);
        if (BeanUtil.isEmpty(code)) {
            return R.error("验证码已过期");
        }
        System.out.println(code.equals(guestSessions.getCode()));
        if (!code.equals(guestSessions.getCode())) {
            return R.error("验证码不正确");
        }
        // 4. BMI计算
        BigDecimal heightCm = guestSessions.getHeightCm();
        BigDecimal weightKg = guestSessions.getWeightKg();
        if (heightCm == null || weightKg == null || heightCm.compareTo(BigDecimal.ZERO) <= 0 || weightKg.compareTo(BigDecimal.ZERO) <= 0) {
            return R.error("身高和体重必须大于0");
        }
        BigDecimal heightM = heightCm.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal bmi = weightKg.divide(heightM.multiply(heightM), 2, BigDecimal.ROUND_HALF_UP);
        guestSessions.setBmiValue(bmi);
        // 5. 存入数据库
        guestSessions.setCode(null);
        GuestSessions entity = BeanUtil.copyProperties(guestSessions, GuestSessions.class);
        guestSessionsMapper.insert(entity);
        return R.ok("注册成功");
    }

    @Override
    public R<String> getCode(String email) {
        // 1. 生成四位数验证码
        String code = String.valueOf((int) (Math.random() * 9000) + 1000);
        // 2. 存入redis
        // 设置5分钟过期时间
        redisTemplate.opsForValue().set("code:" + email, code, Duration.ofMinutes(5));
        // 3. 发送邮箱处理
        if (BeanUtil.isEmpty(email)) {
            return R.error("邮箱不能为空");
        }
        if (!email.contains("@")) {
            return R.error("邮箱格式不正确");
        }
        if (!email.contains(".")) {
            return R.error("邮箱格式不正确");
        }
        if (email.length() < 5) {
            return R.error("邮箱格式不正确");
        }
        // 4. 发送验证码
        MailUtil.send(email, "验证码", "您的验证码是：" + code, false, null);
        return R.ok("验证码已发送至邮箱，请注意查收");
    }
}