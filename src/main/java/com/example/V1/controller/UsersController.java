package com.example.V1.controller;


import com.example.V1.commont.Result;
import com.example.V1.entity.Users;
import com.example.V1.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取维护人员
     */
    @GetMapping("/get-user")
    public Result<List<Users>> getUser(@RequestParam(value ="id",  required = false) Integer id,
                                       @RequestParam(value ="userName", required = false)String userName){
        return usersService.getUser(id,userName);
    }

    /**
     * 修改人员接口
     */
    @PostMapping("/update-user")
    public Result<String> updateUser(@RequestBody Users users){
        return usersService.updateUser(users);
    }
}
