package com.lxl.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lxl.demo.domain.User;
import com.lxl.demo.service.UserService;
import com.lxl.utility.data.Result;
import com.lxl.utility.markup.RepeatSubmit;
import com.lxl.utility.markup.SensitiveLikeQuery;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author liuxinlei
 * @Description
 * @date 2024/4/28 17:57
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/list")
    @RepeatSubmit(interval = 50000)
//    @SensitiveLikeQuery
    public Result<List<User>> getUserList(User user) {
        return Result.success(userService.list(new LambdaQueryWrapper<User>()
                .eq(StringUtils.isNotEmpty(user.getPassword()), User::getPassword, user.getPassword())
                .eq(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                .eq(Objects.nonNull(user.getAmount()), User::getAmount, user.getAmount())));
    }

    @PostMapping("/user/getOne")
    @SensitiveLikeQuery(business_key = "user")
    public Result<User> getUser(@RequestBody User user) {
        return Result.success(userService.getOne(new LambdaQueryWrapper<User>()
                .eq(StringUtils.isNotEmpty(user.getPassword()), User::getPassword, user.getPassword())
                .eq(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername()), false));
    }

    @GetMapping("/save")
    public String setUser(@RequestBody User user) {
        userService.save(user);
        return "success";
    }

}
