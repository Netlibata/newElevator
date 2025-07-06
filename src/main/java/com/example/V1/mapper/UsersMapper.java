package com.example.V1.mapper;

import com.example.V1.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 人员信息表 Mapper 接口
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface UsersMapper extends BaseMapper<Users> {

    // 根据用户名查询用户
    Users selectByUserName(String userName);

    // 获取所有用户
    List<Users> selectAll();
}
