package com.fruit.controller;

import com.fruit.entity.dto.PageRequestDTO;
import com.fruit.entity.po.Tasks;
import com.fruit.result.R;
import com.fruit.service.ITaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Zhang-986
 * @date 2025/6/2
 */

@RestController
@Tag(name = "TaskController", description = "任务控制器")
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ITaskService iTaskService;
    @Operation(summary = "增加任务", description = "增加任务接口")
    @PostMapping("/addTask")
    public R<String> addTask(@RequestBody Tasks tasks){
        log.info("{}", tasks);
        return iTaskService.addTask(tasks);
    }
    @Operation(summary = "用户查询任务接口", description = "查询任务接口")
    @GetMapping("/getTask")
    public R<List<Tasks>> getTask() {
        return iTaskService.getTask();
    }
    @Operation(summary = "编辑任务接口" ,description = "编辑任务接口")
    @PutMapping("/editTask")
    public R<String> editTask(@RequestBody Tasks tasks) {
        log.info("编辑任务: {}", tasks);
        return iTaskService.editTask(tasks);
    }
    @Operation(summary = "删除任务接口", description = "删除任务接口")
    @DeleteMapping("/deleteTask/{id}")
    public R<String> deleteTask(@PathVariable Long id) {
        log.info("删除任务: {}", id);
        return iTaskService.deleteTask(id);
    }
    @Operation(summary = "管理员查询任务接口", description = "管理员查询任务接口")
    @GetMapping("/getAllTask")
    public R<PageInfo<Tasks>> getAllTask(PageRequestDTO pageRequestDTO ) {
        log.info("管理员查询任务: {}", pageRequestDTO);
        PageInfo<Tasks> tasksPageInfo = iTaskService.getAllTask(pageRequestDTO);
        return R.ok(tasksPageInfo);
    }

}