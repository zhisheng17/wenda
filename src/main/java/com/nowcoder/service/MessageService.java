package com.nowcoder.service;

import com.nowcoder.dao.MessageDAO;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 10412 on 2016/8/4.
 * 私信模块
 */
@Service
public class MessageService
{
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;


    /**
     * 发送私信
     * htmlEscape HTML特殊字符替换
     * sensitiveService.filter 敏感词过滤
     * @param message
     * @return
     */
    public int addMessage(Message message)
    {
        //站内信时也要过滤敏感词
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    /**
     * 获取私信详情
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationDetail(String conversationId, int offset, int limit)
    {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    /**
     * 获取私信列表
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList(int userId, int offset, int limit)
    {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    /**
     * 获取私信未读数量
     * @param userId
     * @param conversationId
     * @return
     */
    public int getConversationUnreadCount(int userId, String conversationId)
    {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

}
