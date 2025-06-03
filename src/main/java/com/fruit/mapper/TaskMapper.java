package com.fruit.mapper;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Tasks;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    void add(Tasks tasks);


    List<Tasks> getTaskById(long start);

    void edit(Tasks tasks);

    void delete(Long id);

    List<Tasks> getAllTask(PageRequestDTO pageRequestDTO);
}
