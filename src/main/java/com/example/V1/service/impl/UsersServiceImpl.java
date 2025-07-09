package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.V1.commont.Result;
import com.example.V1.entity.Users;
import com.example.V1.mapper.UsersMapper;
import com.example.V1.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;

import java.security.SecureRandom;
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

    @Override
    public Result<String> addUser(Users users, HttpServletRequest request) {
        log.info("接收到数据：{}", users);

        // 生成一个盐
        String salt = generateSalt();
        users.setSalt(salt); // 保存到数据库

        // 对密码进行加盐 SHA-1 加密
        String encryptedPassword = encryptPasswordWithSalt(users.getPassword(), salt);
        users.setPassword(encryptedPassword);

        boolean save = this.save(users);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    //加盐
    private String generateSalt() {
        // 生成一个16位随机盐
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    //SHA-1加密
    private String encryptPasswordWithSalt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            String saltedPassword = password + salt;
            byte[] hashedBytes = digest.digest(saltedPassword.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            log.error("SHA1加盐加密失败", e);
            return null;
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
    public Result<List<Users>> getUser(Integer id, String userName,String condition) {
        try {
            LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();

            // 条件拼接
            if (id != null) {
                queryWrapper.eq(Users::getId, id);
            }
            if (userName != null && !userName.isEmpty()) {
                queryWrapper.like(Users::getUserName, userName);
            }
            if(condition != null && !condition.isEmpty()){
                queryWrapper.eq(Users::getCondition, condition);
            }

            List<Users> users;
            if (id == null && (userName == null || userName.isEmpty())&& (condition == null||condition.isEmpty())) {
                users = this.list(); // 查询全部
                return Result.success("查询全部用户成功", users);
            } else {
                users = this.list(queryWrapper); // 按条件查询
                return Result.success("查询成功", users);
            }
        } catch (Exception e) {
            System.out.println("错误信息：" + e.getMessage());
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

    /**
     * 登录
     */
    @Override
    public Result<String> login(Users users, HttpServletRequest request) {
        String password = users.getPassword();
        //Integer id = users.getId();
        String userPhone = users.getUserPhone();
        String email = users.getEmail();

        Users user = this.getOne(new LambdaQueryWrapper<Users>()
                .and(wrapper -> wrapper
                        .eq(userPhone != null, Users::getUserPhone, userPhone)
                        .or()
                        .eq(email != null, Users::getEmail, email)
                ));
        if(user == null){
            return Result.error("用户不存在");
        }

        // 用数据库存的盐，对前端传的密码加盐加密后，和数据库密码比对
        String encryptedInputPassword = encryptPasswordWithSalt(password, user.getSalt());
        if (encryptedInputPassword != null && !encryptedInputPassword.equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        // 验证成功，保存用户到 session（自动给浏览器返回 JSESSIONID）
        request.getSession().setAttribute("user", user);

        return Result.success("登录成功",user.getRole());
    }

    /**
     * 获取当前登录用户
     */
    @Override
    public Result<Users> getLoginUser(HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) return Result.error("未登录");
        return Result.success("已登录", user);
    }

    /**
     * 登出接口
     */
    @Override
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().invalidate(); // 清空 Session
        return Result.success("退出登录成功");
    }
}
