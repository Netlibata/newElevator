package com.example.V1.service.impl;

import com.example.V1.commont.Result;
import com.example.V1.entity.Users;
import com.example.V1.mapper.UsersMapper;
import com.example.V1.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
