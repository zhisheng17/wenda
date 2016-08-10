package com.nowcoder.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10412 on 2016/8/10.
 */
public class EventModel
{
    private EventType type;         //触发者类型
    private int actorId;            //触发者
    private int entityType;         //触发对象
    private int entityId;
    private int entityOwnerId;

    private Map<String, String> exts = new HashMap<String, String>();


    public EventModel setExt(String key, String value)
    {
        exts.put(key, value);
        return this;
    }

    public EventModel()
    {

    }

    public EventModel(EventType type)
    {
        this.type = type;
    }

    public String getExt(String key)
    {
        return exts.get(key);
    }

    public EventType getType()
    {
        return type;
    }

    public EventModel setType(EventType type)
    {
        this.type = type;
        return this;
    }

    public int getActorId()
    {
        return actorId;
    }

    public EventModel setActorId(int actorId)
    {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType()
    {
        return entityType;
    }

    public EventModel setEntityType(int entityType)
    {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId()
    {
        return entityId;
    }

    public EventModel setEntityId(int entityId)
    {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId()
    {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId)
    {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts()
    {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts)
    {
        this.exts = exts;
        return this;
    }
}
