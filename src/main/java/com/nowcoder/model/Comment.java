package com.nowcoder.model;

import java.util.Date;

/**
 * Created by 10412 on 2016/8/4.
 * 评论模块
 */
public class Comment
{
    /**
     * userId 用户id
     * entityId 实体id
     * entityType 实体类型
     * content  评论内容
     * createdDate 评论日期
     * status 评论状态（1代表删除，0代表存在）
     */
    private int id;
    private int userId;
    private int entityId;
    private int entityType;
    private String content;
    private Date createdDate;
    private int status;

    //get and set function

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
