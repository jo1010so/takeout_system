package com.jojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author QBX
* @description 针对表【Order】的数据库操作Mapper
* @createDate 2024-12-24 09:43:49
* @Entity com.jojo.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




