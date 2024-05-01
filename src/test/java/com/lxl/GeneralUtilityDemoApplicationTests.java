package com.lxl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lxl.demo.domain.User;
import com.lxl.demo.mapper.UserMapper;
import com.lxl.demo.service.UserService;
import com.lxl.utility.cache.GlobalCache;
import com.lxl.utility.cache.ThreadCache;
import com.lxl.demo.parse.handler.DataFormatHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class GeneralUtilityDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GlobalCache globalCache;

    @Autowired
    private ThreadCache threadCache;

    @Autowired
    private DataFormatHandler dataFormatHandler;

    @Test
    void setUserEntity() {

        User user = new User();
        user.setUsername("刘欣欣");
        user.setPassword("P@ss5d");
//        user.setCreateTime(LocalDateTime.now());

        userService.save(user);

    }

    @Test
    void getUserEntity() {
//        System.out.println("userService.list() = " + userService.list(new LambdaQueryWrapper<User>()
//                .orderByDesc(User::getId)));
//        System.out.println(userMapper.selectList(new QueryWrapper<User>()));
        List<User> list = userService.list(new LambdaQueryWrapper<User>()
                .orderByDesc(User::getCreateTime));
        dataFormatHandler.webLabelConversion(list,User.class);
        System.out.println(list);
    }

    @Test
    void setRedis(){
        redisTemplate.opsForValue().set("root","111");

    }

    @Test
    void getRedis(){
        System.out.println(redisTemplate.opsForValue().get("root"));
        redisTemplate.delete("root");
    }

}
