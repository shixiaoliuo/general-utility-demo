package com.lxl.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxl.demo.domain.User;
import com.lxl.demo.service.UserService;
import com.lxl.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author LiuXinLei
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-28 17:56:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




