package com.qf.controller;

import com.qf.commons.CodeUtils;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册页面点击发送验证码，传送邮箱到后端，发送验证码到邮箱
     */
    @RequestMapping("/sendcode")
    @ResponseBody
    public String sendCode(String email, HttpServletRequest request){

        String code = CodeUtils.getCode();
        HttpSession session = request.getSession();
        session.setAttribute("code",code);
        session.setAttribute("email",email);
        String msg = userService.sendCode(code,email);

        return msg;
    }

    /**
     * 异步校验 注册验证码与邮箱验证码是否一致
     */
    @RequestMapping("/checkoutcode")
    @ResponseBody
    public String checkoutCode(String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessioncode = (String) session.getAttribute("code");
        if(code.equals(sessioncode)){
            return "1";
        }

        return "0";
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(User user){
        int result = userService.insert(user);
        if(result>0){
            return "section1";
        }
        return "section2";
    }

    /**
     * 用户登录
     */
    @RequestMapping("/login")
    public String login(User user){

        User curuser = userService.selectOne(user);

        return "welcome";
    }

    /**
     * 校验改密验证码
     */
    @RequestMapping("/tosetpassword")
    public String toSetPassword(String email, String code,HttpServletRequest request){
        String result = checkoutCode(code , request);
        if("1".equals(result)){
            return "setpassword";
        }
        return "error";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/setpassword")
    public String setPassword(String code,String password,HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String email = (String) session.getAttribute("email");
            String curcode = (String) session.getAttribute("code");
            userService.uploadPassword(email,password);
            return "success";
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 结果跳转页面
     */
    @RequestMapping("/results")
    public String results(){
        return "index";
    }
}
