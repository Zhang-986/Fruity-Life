package com.fruit.service;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Tasks;
import com.fruit.result.R;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Zhang-986
 * @date 2025/6/2
 */


public interface ITaskService{


    R<String> addTask(Tasks tasks);

    R<List<Tasks>> getTask();

    R<String> editTask(Tasks tasks);

    R<String> deleteTask(Long id);

    PageInfo<Tasks> getAllTask(PageRequestDTO pageRequestDTO);
}