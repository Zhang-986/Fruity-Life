package com.fruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Tasks;
import com.fruit.mapper.TaskMapper;
import com.fruit.result.R;
import com.fruit.service.ITaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Zhang-986
 * @date 2025/6/2
 */

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final TaskMapper taskMapper;
    private final RedisTemplate<String,String> template;
    private final String key = "statistics:task:count";

    @Override
    public R<String> addTask(Tasks tasks) {
        // 1.判断任务是否为空
        if (BeanUtil.isEmpty(tasks)) {
            return R.error("任务信息不能为空");
        }
        // 2.添加任务
        taskMapper.add(tasks);

        template.opsForValue().increment(key);
        return R.ok("任务添加成功");
    }

    @Override
    public R<List<Tasks>> getTask() {
        // 1.查询任务
        List<Tasks> list = taskMapper.getTaskById(1L);
        // 2.查询到结果并且返回
        if (list != null && !list.isEmpty()) {
            return R.ok(list);
        }
        return R.ok(list);
    }

    @Override
    public R<String> editTask(Tasks tasks) {
        // 1.判断任务是否为空
        if (BeanUtil.isEmpty(tasks)) {
            return R.error("任务信息不能为空");
        }
        // 2.编辑任务
        taskMapper.edit(tasks);
        return R.ok("任务编辑成功");
    }

    @Override
    public R<String> deleteTask(Long id) {
        // 1.判断ID是否为空
        if (id == null) {
            return R.error("任务ID不能为空");
        }
        taskMapper.delete(id);
        template.opsForValue().increment(key,-1);
        return R.ok("任务删除成功");
    }

    @Override
    public PageInfo<Tasks> getAllTask(PageRequestDTO pageRequestDTO) {
        // 1.设置分页参数
        PageHelper.startPage(pageRequestDTO.getPageNum(), pageRequestDTO.getPageSize());
        // 2.执行查询
        List<Tasks> tasks = taskMapper.getAllTask(pageRequestDTO);
        // 3.返回结果
        if (tasks != null && !tasks.isEmpty()) {
            return new PageInfo<>(tasks);
        }
        // 如果没有数据，返回空的PageInfo对象
        return new PageInfo<>(List.of());
    }
}