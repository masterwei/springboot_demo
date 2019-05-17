package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.commons.MailUtil;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String sendCode(String code,String email) {
        System.out.println("验证码:"+code);
        System.out.println("邮箱:"+email);
        try {
            MailUtil.sendMail(code,email);
            return "验证码已发送";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "验证码发送失败";
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User selectOne(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.eq("password",user.getPassword());

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int uploadPassword(String email,String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",email);
        User user = userMapper.selectOne(queryWrapper);
        user.setPassword(password);

        return userMapper.updateById(user);
    }

}
