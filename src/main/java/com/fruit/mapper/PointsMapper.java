package com.fruit.mapper;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Fruits;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PointsMapper {

    Long getPointsByUserId(Long userId);

    void insertPoints(Long id);
}
