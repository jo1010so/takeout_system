package com.jojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.domain.Address;
import org.apache.ibatis.annotations.Mapper;

/**
* @author QBX
* @description 针对表【Address】的数据库操作Mapper
* @createDate 2024-12-24 09:43:48
* @Entity com.jojo.domain.Address
*/
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}




