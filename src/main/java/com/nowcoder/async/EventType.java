package com.nowcoder.async;

/**
 * Created by 10412 on 2016/8/10.
 */
public enum EventType
{
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5),
    ADD_QUESTION(6);

    private int value;
    EventType(int value) {this.value = value;}
    public int getValue()
    {
        return value;
    }


}
