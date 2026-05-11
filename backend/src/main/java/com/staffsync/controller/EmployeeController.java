package com.staffsync.controller;

import com.staffsync.common.Result;
import com.staffsync.model.Employee;
import com.staffsync.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 获取员工列表
     */
    @GetMapping
    public Result<List<Employee>> list() {
        return Result.success(employeeService.list());
    }

    /**
     * 获取单个员工
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return Result.error("员工不存在");
        }
        return Result.success(employee);
    }

    /**
     * 新增员工
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody Employee employee) {
        employeeService.create(employee);
        return Result.success("新增成功", null);
    }

    /**
     * 更新员工
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        if (employeeService.getById(id) == null) {
            return Result.error("员工不存在");
        }
        employee.setId(id);
        employeeService.update(employee);
        return Result.success("更新成功", null);
    }

    /**
     * 删除员工
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (employeeService.getById(id) == null) {
            return Result.error("员工不存在");
        }
        employeeService.delete(id);
        return Result.success("删除成功", null);
    }
}
