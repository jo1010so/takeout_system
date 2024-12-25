package com.jojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojo.domain.Address;
import com.jojo.mapper.AddressMapper;
import com.jojo.service.AddressService;
import org.springframework.stereotype.Service;

/**
* @author QBX
* @description 针对表【Address】的数据库操作Service实现
* @createDate 2024-12-24 09:43:48
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




