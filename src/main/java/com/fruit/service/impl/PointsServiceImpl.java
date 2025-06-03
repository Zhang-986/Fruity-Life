package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fruit.entity.dto.PointsDTO;
import com.fruit.entity.po.UserPointBehavior;
import com.fruit.entity.po.UserPoints;
import com.fruit.mapper.PointsBehaviorMapper;
import com.fruit.mapper.PointsMapper;
import com.fruit.service.IPointsService;
import com.fruit.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.support.CompositeUriComponentsContributor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/6/1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PointsServiceImpl implements Serializable, IPointsService {
    @Serial
    private static final long serialVersionUID = 1L;
    private final PointsMapper pointsMapper;
    private final CompositeUriComponentsContributor compositeUriComponentsContributor;
    private final PointsBehaviorMapper pointsBehaviorMapper;

    @Override
    public Long getPoints() {
        //1.获得当前用户ID
        Long userId = UserContext.getUserId();
        //2.查询当前用户积分
        return pointsMapper.getPointsByUserId(userId);
    }
    @Transactional
    @Override
    public Long addPoints(PointsDTO pointsDTO) {
        // 1. 获取当前用户ID
        Long userId = UserContext.getUserId();
        // 2. 添加对应行为积分
        if(BeanUtil.isEmpty(pointsDTO)){
            throw new RuntimeException("积分信息不能为空");
        }
        UserPoints userPoints = BeanUtil.copyProperties(pointsDTO, UserPoints.class);
        userPoints.setUserId(userId);
        pointsMapper.updateUserPointsById(userPoints);
        // 3. 添加积分行为记录
        Long points = userPoints.getPoints();

        UserPointBehavior pointsBehavior = new UserPointBehavior();
        pointsBehavior.setUserId(userId);
        pointsBehavior.setBehaviorType(pointsDTO.getPointsType());
        pointsBehavior.setPointsChange(pointsDTO.getPoints());
        pointsBehavior.setPointsBalance(points);

        pointsBehaviorMapper.insertPointsBehavior(pointsBehavior);

        return points;
    }
}