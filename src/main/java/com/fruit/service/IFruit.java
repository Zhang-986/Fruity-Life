package com.fruit.service;

import com.fruit.entity.Fruits;

public interface IFruit {
    void insert(Fruits fruit);

    void deleteById(Long id);

    Fruits getById(Long id);

    void update(Fruits fruit);
}
