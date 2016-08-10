package com.nowcoder.async;

import java.util.List;

/**
 * Created by 10412 on 2016/8/10.
 */
public interface EventHandler
{
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();

}
