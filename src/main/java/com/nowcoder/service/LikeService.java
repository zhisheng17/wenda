package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 10412 on 2016/8/4.
 * 点赞点踩模块
 */
@Service
public class LikeService
{
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 根据实体id和实体类型获取点赞数量
     * @param entityType
     * @param entityId
     * @return 点赞数量
     */
    public long getLikeCount(int entityType, int entityId)
    {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId, int entityType, int entityId)
    {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismenber(likeKey, String.valueOf(userId)))
        {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismenber(disLikeKey, String.valueOf(userId)) ? -1 : 0;

    }




    public long like(int userId, int entityType, int entityId)
    {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));


        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));


        return jedisAdapter.scard(likeKey);
    }


    public long disLike(int userId, int entityType, int entityId)
    {

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));


        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));


        return jedisAdapter.scard(likeKey);
    }
}
