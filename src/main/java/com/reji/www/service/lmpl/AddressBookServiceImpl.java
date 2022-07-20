package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.entity.AddressBook;
import com.reji.www.mapper.AddressBookMapper;
import com.reji.www.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
