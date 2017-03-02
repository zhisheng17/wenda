package com.nowcoder.model;

import java.util.Date;

/**
 * Created by 10412 on 2016/8/4.
 * 私信模块
 */
public class Message
{
    /**
     * fromId  私信发送者
     * toId  私信接收者
     * content  私信内容
     * createdDate  发送日期
     * hasRead  是否阅读（状态）
     * conversationId 私信id = 发送者_接收者（接收者_发送者）
     */
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    //私信id = 发送者_接收者（接收者_发送者） 谁小谁排在前面
    public String getConversationId() {
        if (fromId < toId)
        {
            return String.format("%d_%d", fromId, toId);
        }
        else
        {
            return String.format("%d_%d", toId, fromId);
        }
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
