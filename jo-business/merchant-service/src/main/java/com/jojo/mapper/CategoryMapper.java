package com.jojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author QBX
* @description 针对表【Category】的数据库操作Mapper
* @createDate 2024-12-24 09:43:48
* @Entity com.jojo.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




