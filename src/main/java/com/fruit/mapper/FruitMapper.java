package com.fruit.mapper;

import com.fruit.entity.Fruits;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FruitMapper {
    @Insert("insert into fruits (name, description, nutrition_summary, flavor_profile, image_url, season_info, selection_tips, storage_tips) " +
            "values (#{name}, #{description}, #{nutritionSummary}, #{flavorProfile}, #{imageUrl}, #{seasonInfo}, #{selectionTips}, #{storageTips})")
    void insert(Fruits fruit);
}
