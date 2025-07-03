package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.entity.Users;
import com.example.V1.mapper.UsersMapper;
import com.example.V1.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 人员信息表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    /**
     * 添加维修人员
     */
    @Override
    public Result<String> addUser(Users users) {
        log.info("接收到数据：{}", users);
        boolean save = this.save(users);
        if(save){
            return Result.success("添加成功");
        }
        else{
            return Result.error("添加失败");
        }
    }

    /**
     * 删除人员
     */
    @Override
    public Result<String> deleteUser(Integer id) {
        try{
            LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Users::getId, id);
            boolean remove = this.remove(queryWrapper);
            if(remove){
                return Result.success("删除成功");
            }
            else{
                return Result.error("删除失败");
            }
        }catch(Exception e){
            return Result.error("系统异常，出现错误");
        }
    }

    /**
     * 获取人员信息
     */
    @Override
    public Result<List<Users>> getUser(Integer id, String userName) {
        try{
            LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
            if(id == null){
                queryWrapper.eq(Users::getId, id);
            }
            if(userName != null||!userName.isEmpty()){
                queryWrapper.like(Users::getUserName, userName);
            }
            // 如果都没传，说明查全部
            if (id == null && (userName == null || userName.isEmpty())) {
                List<Users> users = this.list(); // 查询全部
                return Result.success("查询全部用户成功", users);
            }
            Users user = this.getOne(queryWrapper);
            return Result.success("查询成功", Collections.singletonList(user));
        }catch (Exception e) {
            return Result.error("系统异常，出现错误");
        }
    }

    /**
     * 修改人员信息
     */
    @Override
    public Result<String> updateUser(Users users) {
        try{
            LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Users::getId, users.getId());
            boolean update = this.update(users, queryWrapper);
            if(update){
                return Result.success("修改成功");
            }
            else{
                return Result.error("修改失败");
            }
        }catch(Exception e){
            return Result.error("系统异常，出现错误");
        }
    }

}
