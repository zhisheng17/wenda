package com.nowcoder.service;

import com.nowcoder.dao.FeedDAO;
import com.nowcoder.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 10412 on 2016/9/10.
 * feed 模块
 */
@Service
public class FeedService
{
    @Autowired
    FeedDAO feedDAO;


    /**
     * 根据 id 查询 feed 流
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count)
    {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    /**
     * 增加 feed 流
     * @param feed
     * @return
     */
    public boolean addFeed(Feed feed)
    {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    /**
     * 根据 id 查询 feed 流
     * @param id
     * @return
     */
    public Feed getById(int id)
    {
        return feedDAO.getFeedById(id);
    }
}
