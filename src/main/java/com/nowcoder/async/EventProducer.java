package com.nowcoder.async;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 10412 on 2016/8/10.
 */
@Service
public class EventProducer
{
    @Autowired
    JedisAdapter jedisAdapter;


    public boolean fireEvent(EventModel eventModel)
    {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);


            return true;
        }catch (Exception e)
        {
            return false;
        }

    }


}
