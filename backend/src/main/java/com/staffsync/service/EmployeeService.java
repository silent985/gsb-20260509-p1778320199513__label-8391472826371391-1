package com.staffsync.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.staffsync.mapper.EmployeeMapper;
import com.staffsync.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理服务
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    /**
     * 获取员工列表
     */
    public List<Employee> list() {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Employee::getCreatedAt);
        return employeeMapper.selectList(wrapper);
    }

    /**
     * 分页获取员工列表
     */
    public Page<Employee> page(int pageNum, int pageSize, String keyword) {
        Page<Employee> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Employee::getName, keyword)
                    .or()
                    .like(Employee::getPhone, keyword);
        }

        wrapper.orderByDesc(Employee::getCreatedAt);
        return employeeMapper.selectPage(page, wrapper);
    }

    /**
     * 获取单个员工
     */
    public Employee getById(Long id) {
        return employeeMapper.selectById(id);
    }

    /**
     * 新增员工
     */
    public void create(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employeeMapper.insert(employee);
    }

    /**
     * 更新员工
     */
    public void update(Employee employee) {
        employee.setUpdatedAt(LocalDateTime.now());
        employeeMapper.updateById(employee);
    }

    /**
     * 删除员工
     */
    public void delete(Long id) {
        employeeMapper.deleteById(id);
    }
}
