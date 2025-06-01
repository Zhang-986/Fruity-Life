package com.fruit.service;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Fruits;
import com.fruit.result.R;
import com.github.pagehelper.PageInfo;

public interface IFruit {
    void insert(Fruits fruit);

    void deleteById(Long id);

    Fruits getById(Long id);

    void update(Fruits fruit);

    R<PageInfo<Fruits>> getFruits(PageRequestDTO page);

    Fruits getByName(String name);
}
