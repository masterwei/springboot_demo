package com.qf.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {

    @Autowired
    private static JavaMailSender javaMailSender;

    /**
     * 发送邮件
     */
    public static void sendMail(String code,String email) throws MessagingException {

        //设置邮件服务器
        Properties properties = new Properties();
        //可以设置邮件服务器
        properties.setProperty("mail.host","smtp.sina.com");
        properties.setProperty("mail.smtp.auth","true");
        //与邮件服务器的连接
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("verygoodwlk@sina.cn","ken123456");
            }
        });

        //创建邮件
        Message message = new MimeMessage(session);
        //设置收件人地址
        message.setFrom(new InternetAddress("verygoodwlk@sina.cn"));
        //抄送
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //设置邮件的主体
        message.setSubject("来自注册验证中心");
        //设置内容
        String msg="<h1>账号验证码："+code+"<h1>";
        message.setContent(msg, "text/html;charset=utf-8");
        //发送邮件
        Transport.send(message);

    }

}
