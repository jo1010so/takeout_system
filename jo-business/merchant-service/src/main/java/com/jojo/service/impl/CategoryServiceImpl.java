package com.jojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojo.domain.Category;
import com.jojo.mapper.CategoryMapper;
import com.jojo.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author QBX
* @description 针对表【Category】的数据库操作Service实现
* @createDate 2024-12-24 09:43:48
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




