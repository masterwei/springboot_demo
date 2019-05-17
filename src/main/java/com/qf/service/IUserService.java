package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    String sendCode(String code,String email);

    int insert(User user);

    User selectOne(User user);

    int uploadPassword(String email,String password);

}
