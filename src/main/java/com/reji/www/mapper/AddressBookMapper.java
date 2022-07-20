package com.reji.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reji.www.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
