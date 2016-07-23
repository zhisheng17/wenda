package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10412 on 2016/7/22.
 */
public class ViewObject
{
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value)
    {
        objs.put(key, value);
    }

    public Object get(String key)
    {
        return objs.get(key);
    }
}
