package com.nowcoder.model;

import java.util.Date;

/**
 * Created by 10412 on 2016/7/3.
 * LoginTicket
 */
public class LoginTicket {
    /**
     * userId 用户id
     * expired 过期时间
     * status  状态：0有效，1无效
     * ticket
     */
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

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

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
