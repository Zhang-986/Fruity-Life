package com.fruit.mapper;

import com.fruit.entity.po.UserPointBehavior;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointsBehaviorMapper {

    void insertPointsBehavior(UserPointBehavior pointsBehavior);
}
