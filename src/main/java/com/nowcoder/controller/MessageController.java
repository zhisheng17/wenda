package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10412 on 2016/8/4.
 */
@Controller
public class MessageController
{
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList()
    {
        return "letter";
    }




    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId)
    {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for (Message message : messageList)
            {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
//                vo.set("user", userService.getUser(message.getFromId()));

                User user = userService.getUser(message.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());

                messages.add(vo);
            }
            model.addAttribute("messages", messages);

        }catch (Exception e)
        {
            logger.error("获取详情消息失败"+e.getMessage());
        }


        return "letterDetail";
    }




    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content)
    {
        try {
            if (hostHolder.getUser() == null)
            {
                return WendaUtil.getJSONString(999, "未登录");
            }


            User user = userService.selectByName(toName);
            if (user == null)
            {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();

            message.setCreatedDate(new Date());
            message.setContent(content);
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());

            messageService.addMessage(message);

            return WendaUtil.getJSONString(0);





        }catch (Exception e)
        {
            logger.error("增加站内信失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "发送失败！");
        }


//        return "redirect:/question/" + questionId;
    }




}
