package com.staffsync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.staffsync.model.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工数据访问接口
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
