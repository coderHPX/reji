package com.reji.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reji.www.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
