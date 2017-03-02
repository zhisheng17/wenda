package com.nowcoder.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by 10412 on 2016/9/10.
 * feed 流
 */
public class Feed
{
    /**
     * type  feed 类型
     * userId  用户id
     * createdDate feed流创建时间
     */
    private int id;
    private int type;
    private int userId;
    private Date createdDate;

    //JSON解析包 这里用的是阿里巴巴的 fastjson
    //功能强大，完全支持Java Bean、集合、Map、日期、Enum，支持范型，支持自省；无依赖，速度最快
    //源码地址：https://github.com/alibaba/fastjson
    private String data;

    private JSONObject dataJSON = null;

    //get and set function

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }

    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }
}
