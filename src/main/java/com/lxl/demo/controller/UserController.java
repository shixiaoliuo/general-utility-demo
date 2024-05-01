package com.lxl.demo.controller;

import com.lxl.demo.domain.User;
import com.lxl.demo.service.UserService;
import com.lxl.utility.data.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @Description
 * @author liuxinlei
 * @date 2024/4/28 17:57
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/list")
    public Result<List<User>> getUserList(){
        return Result.success(userService.list());
    }

    @GetMapping("/save")
    public String setUser(){
        userService.save(new User().setUsername("刘欣欣").setPassword("123456"));
        return "success";
    }

}
