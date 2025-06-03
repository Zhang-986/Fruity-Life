package com.fruit.mapper;

import com.fruit.entity.po.UserPoints;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointsMapper {

    Long getPointsByUserId(Long userId);

    void insertPoints(Long id);

    void updateUserPointsById(UserPoints userPoints);
}
