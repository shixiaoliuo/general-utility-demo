package com.lxl.demo.mapper;

import com.lxl.demo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author LiuXinLei
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-04-28 17:56:32
* @Entity com.lxl.demo.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




