package com.nowcoder.service;

import com.nowcoder.dao.MessageDAO;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 10412 on 2016/8/4.
 */
@Service
public class MessageService
{
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;




    public int addMessage(Message message)
    {
        //站内信时也要过滤敏感词
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }


    public List<Message> getConversationDetail(String conversationId, int offset, int limit)
    {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }


    public List<Message> getConversationList(int userId, int offset, int limit)
    {
        return messageDAO.getConversationList(userId, offset, limit);
    }


    public int getConvesationUnreadCount(int userId, String conversationId)
    {
        return messageDAO.getConvesationUnreadCount(userId, conversationId);
    }

}
