package com.jojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojo.domain.Order;
import com.jojo.mapper.OrderMapper;
import com.jojo.service.OrderService;
import org.springframework.stereotype.Service;

/**
* @author QBX
* @description 针对表【Order】的数据库操作Service实现
* @createDate 2024-12-24 09:43:49
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




