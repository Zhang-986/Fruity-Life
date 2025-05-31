package com.fruit.mapper;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Fruits;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FruitMapper {
    //添加水果
    @Insert("insert into fruits (name, description, nutrition_summary, flavor_profile, image_url, season_info, selection_tips, storage_tips) " +
            "values (#{name}, #{description}, #{nutritionSummary}, #{flavorProfile}, #{imageUrl}, #{seasonInfo}, #{selectionTips}, #{storageTips})")
    void insert(Fruits fruit);
   //删除水果
    @Delete("delete from fruits where id = #{id}")
    void deleteById(Long id);
    // 查询水果信息
    @Select("select * from fruits where id = #{id}")
    Fruits getById(Long id);

    //修改水果信息

    void update(Fruits fruit);

    List<Fruits> getFruits(PageRequestDTO page);
}
