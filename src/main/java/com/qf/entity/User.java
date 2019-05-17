package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private int status = 0 ; //默认未激活
    private Date createtime = new Date();

}
