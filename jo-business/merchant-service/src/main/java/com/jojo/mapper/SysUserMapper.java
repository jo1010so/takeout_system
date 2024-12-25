package com.jojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author QBX
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2024-12-24 09:44:48
* @Entity com.jojo.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}




