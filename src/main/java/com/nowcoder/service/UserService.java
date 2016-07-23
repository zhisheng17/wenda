package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 10412 on 2016/7/22.
 */
@Service
public class UserService
{
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;


    public Map<String, String> register(String username, String password)
    {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(username))          //判断用户名是否为空
        {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password))          //判断密码是否为空
        {
            map.put("msg", "密码不能为空！");
            return map;
        }

        //...用户名合法性检测（长度，敏感词，重复，特殊字符）

        User user = userDAO.selectByName(username);
        if (user!=null)
        {
            map.put("msg", "用户名已经被注册！");
            return map;
        }


        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));         //生成随机的salt
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }


    public Map<String, String> login(String username, String password)
    {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(username))          //判断用户名是否为空
        {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password))          //判断密码是否为空
        {
            map.put("msg", "密码不能为空！");
            return map;
        }

        //...用户名合法性检测（长度，敏感词，重复，特殊字符）

        User user = userDAO.selectByName(username);
        if (user == null)
        {
            map.put("msg", "用户名不存在！");
            return map;
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword()))
        {
            map.put("msg", "密码错误！");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public String addLoginTicket(int userId)
    {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();


    }



    public User getUser(int id)
    {
        return userDAO.selectById(id);
    }


}
