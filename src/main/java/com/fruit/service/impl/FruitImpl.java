package com.fruit.service.impl;

import com.fruit.entity.Fruits;
import com.fruit.mapper.FruitMapper;
import com.fruit.service.IFruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@Service
public class FruitImpl implements IFruit,Serializable {
    @Autowired
    private FruitMapper fruitMapper;
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 添加水果
     * @param fruit 水果实体类
     */
    @Override
    public void insert(Fruits fruit) {

        fruitMapper.insert(fruit);
    }
}