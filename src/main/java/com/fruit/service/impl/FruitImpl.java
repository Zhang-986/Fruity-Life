package com.fruit.service.impl;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Fruits;
import com.fruit.mapper.FruitMapper;
import com.fruit.result.R;
import com.fruit.service.IFruit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final String key = "statistics:fruit:count";


    /**
     * 添加水果
     * @param fruit 水果实体类
     */
    @Override
    public void insert(Fruits fruit) {
        fruitMapper.insert(fruit);
        // 水果种类增加量
        redisTemplate.opsForValue().increment(key);

    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        fruitMapper.deleteById(id);
        redisTemplate.opsForValue().increment(key,-1);
    }

    @Override
    public Fruits getById(Long id) {
        return  fruitMapper.getById(id);
    }

    @Override
    public void update(Fruits fruit) {
        fruitMapper.update(fruit);
    }

    @Override
    public R<PageInfo<Fruits>> getFruits(PageRequestDTO page) {
        // 1. 设置分页参数
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        // 2. 执行查询
        List<Fruits> fruits = fruitMapper.getFruits(page);
        PageInfo<Fruits> pageInfo = new PageInfo<>(fruits);
        // 3. 返回结果
        if (pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
            return R.ok(pageInfo);
        }
        // 如果没有数据，返回空的PageInfo对象
        return R.ok(new PageInfo<>());
    }

    @Override
    public Fruits getByName(String name) {
        return fruitMapper.getByName(name);
    }
}