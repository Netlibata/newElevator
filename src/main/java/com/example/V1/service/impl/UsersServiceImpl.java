package com.example.V1.service.impl;

import com.example.V1.entity.Users;
import com.example.V1.mapper.UsersMapper;
import com.example.V1.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员信息表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
