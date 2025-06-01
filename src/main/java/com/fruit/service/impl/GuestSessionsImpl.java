package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailUtil;
import com.fruit.entity.po.GuestSessions;
import com.fruit.entity.dto.GuestSessionsDTO;
import com.fruit.entity.vo.GuestSessionsVo;
import com.fruit.mapper.GuestSessionsMapper;
import com.fruit.mapper.PointsMapper;
import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import com.fruit.utils.JwtUtil;
import com.fruit.utils.PasswordUtils;
import com.fruit.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
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
    private final PointsMapper pointsMapper;

    @Transactional
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
            return R.error("验证码已过期  ");
        }
        System.out.println(code.equals(guestSessions.getCode()));
        if (!code.equals(guestSessions.getCode())) {
            return R.error("验证码不正确");
        }
        // 4. 密码加密
        if (BeanUtil.isEmpty(guestSessions.getPassword())) {
            return R.error("密码不能为空");
        }
        String password = PasswordUtils.encryptPassword(guestSessions.getPassword());
        guestSessions.setPassword(password);
        // 5. 存入数据库
        GuestSessions entity = BeanUtil.copyProperties(guestSessions, GuestSessions.class);
        try {
            guestSessionsMapper.insert(entity);
            guestSessionsMapper.insertGuessSession(entity);
            pointsMapper.insertPoints(entity.getId());
            redisTemplate.opsForValue().set("guest:sessions:completed:" +entity.getId(), "false");

        } catch (DuplicateKeyException e) {
            return R.error("用户已存在，请更换邮箱");
        }
        // 6. 注册人数累积
        String key = "statistics:guest:sessions:register:count";
        redisTemplate.opsForValue().increment(key);
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

    @Override
    public R<String> login(GuestSessionsDTO guestSessions) {
        // 1. 校验
        if (BeanUtil.isEmpty(guestSessions)) {
            return R.error("登录信息不能为空");
        }
        // 2. 校验邮箱格式
        String email = guestSessions.getEmail();
        if (BeanUtil.isEmpty(email) || !email.contains("@") || !email.contains(".") || email.length() < 5) {
            return R.error("邮箱格式不正确");
        }
        // 3. 校验密码
        String password = guestSessions.getPassword();
        if (BeanUtil.isEmpty(password)) {
            return R.error("密码不能为空");
        }
        String encryptedPassword = PasswordUtils.encryptPassword(password);
        // 4. 查询数据库
        GuestSessions entity = guestSessionsMapper.selectByEmailAndPassword(email, encryptedPassword);
        if (entity != null) {
            // 5.JWT返回
            String token = JwtUtil.generateToken(String.valueOf(entity.getId()), true);

            return R.ok(token);
        }
        // 6. 登录失败
        return R.error("邮箱或密码错误");
    }

    @Override
    public R<Boolean> verifyEmail(String email) {
        // 1. 校验邮箱格式
        if (BeanUtil.isEmpty(email) || !email.contains("@") || !email.contains(".") || email.length() < 5) {
            return R.error("邮箱格式不正确");
        }
        // 2. 查询数据库
        GuestSessions flag = guestSessionsMapper.verifyEmail(email);
        if (flag != null) {
            return R.ok(true);
        }
        return R.error("邮箱未注册");
    }

    @Override
    public R<String> handlePassword(GuestSessionsDTO guestSessions) {
        // 1. 校验邮箱
        String email = guestSessions.getEmail();
        if (BeanUtil.isEmpty(email) || !email.contains("@") || !email.contains(".") || email.length() < 5) {
            throw new RuntimeException("邮箱格式不正确");
        }
        // 2. 校验密码
        String password = guestSessions.getPassword();
        String newPwd = PasswordUtils.encryptPassword(password);
        if (BeanUtil.isEmpty(newPwd)) {
            return R.error("密码不能为空");
        }
        // 3. 更改密码
        guestSessionsMapper.updatePasswordByEmail(email, newPwd);
        return R.ok("密码修改成功");
    }

    /*@Override
    public Boolean isCompleted() {
        // 1.获取用户上下文
        Long userId = UserContext.getUserId();
        String key = "guest:sessions:completed:" + userId;
        // 2. 先从Redis中查询是否存在
        String s = redisTemplate.opsForValue().get(key);
        if (s != null) {
            // 如果存在，直接返回
            return Boolean.parseBoolean(s);
        }
        // 3. 查询是否完善个人信息
        GuestSessions guestSessions = guestSessionsMapper.getEntityById(userId);
        // 4 判断是否完善
        redisTemplate.opsForValue().set(key, guestSessions.getIsCompleted().toString());
        return guestSessions.getIsCompleted();
    }*/

    @Override
    public R<String> completeProfile(GuestSessionsDTO guestSessions) {
        // 1. 校验
        if (BeanUtil.isEmpty(guestSessions)) {
            return R.error("完善信息不能为空");
        }
        //2. 获取用户上下文
        Long userId = UserContext.getUserId();
        //3. 将DTO转换为PO
        GuestSessions entity = BeanUtil.copyProperties(guestSessions, GuestSessions.class);
        entity.setId(userId);
        try {
            guestSessionsMapper.updateById(entity);
        } catch (Exception e) {
            return R.error("完善信息失败，请稍后重试");
        }
        redisTemplate.opsForValue().set("guest:sessions:completed:" + userId, "true");
        return R.ok("完善信息成功");
    }

    @Override
    public R<GuestSessionsVo> getEntityById(Long userId) {
        GuestSessions entity = guestSessionsMapper.getEntityById(userId);
        GuestSessionsVo guestSessionsVo = BeanUtil.copyProperties(entity, GuestSessionsVo.class);
        return R.ok(guestSessionsVo);
    }
}