package com.fruit.service.impl;

import com.fruit.mapper.PointsMapper;
import com.fruit.service.IPoints;
import com.fruit.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/6/1
 */
@RequiredArgsConstructor
@Service
public class PointsImpl implements Serializable, IPoints {
    @Serial
    private static final long serialVersionUID = 1L;
    private final PointsMapper pointsMapper;

    @Override
    public Long getPoints() {
        //1.获得当前用户ID
        Long userId = UserContext.getUserId();
        //2.查询当前用户积分
        return pointsMapper.getPointsByUserId(userId);
    }
}