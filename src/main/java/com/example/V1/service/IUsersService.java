package com.example.V1.service;

import com.example.V1.commont.Result;
import com.example.V1.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人员信息表 服务类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface IUsersService extends IService<Users> {

    //添加维修人员
    Result<String> addUser(Users users);

    //删除人员
    Result<String> deleteUser(Integer id);

    //获取人员
    Result <List<Users>> getUser(Integer id, String userName);

    //修改人员信息
    Result<String> updateUser(Users users);
}
