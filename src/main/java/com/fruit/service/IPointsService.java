package com.fruit.service;

import com.fruit.entity.dto.PointsDTO;

public interface IPointsService {
    Long getPoints();

    Long addPoints(PointsDTO pointsDTO);
}
