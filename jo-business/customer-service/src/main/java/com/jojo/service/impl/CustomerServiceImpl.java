package com.jojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojo.domain.Customer;
import com.jojo.mapper.CustomerMapper;
import com.jojo.service.CustomerService;
import org.springframework.stereotype.Service;

/**
* @author QBX
* @description 针对表【Customer】的数据库操作Service实现
* @createDate 2024-12-24 09:43:49
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

}




