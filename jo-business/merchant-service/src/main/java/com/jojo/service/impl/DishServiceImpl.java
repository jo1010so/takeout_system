package com.jojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojo.domain.Dish;
import com.jojo.mapper.DishMapper;
import com.jojo.service.DishService;
import org.springframework.stereotype.Service;

/**
* @author QBX
* @description 针对表【Dish】的数据库操作Service实现
* @createDate 2024-12-24 09:43:49
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}




