package com.example.V1.controller;


import com.example.V1.commont.Result;
import com.example.V1.entity.Users;
import com.example.V1.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 人员信息表 前端控制器
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    /**
     * 添加维护人员
     */
    @PostMapping("/add-user")
    public Result<String> addUser(@RequestBody Users users){
        return usersService.addUser(users);
    }

    /**
     * 删除人员
     */
    @PostMapping("/delete-user")
    public Result<String> deleteUser(@RequestBody Integer id){
        return usersService.deleteUser(id);
    }

}
