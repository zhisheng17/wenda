package com.nowcoder.dao;

import com.nowcoder.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 10412 on 2016/9/11.
 * Mybatis使用注解的配置方式，用接口加注解来实现 Feed流 的 CRUD
 */
@Mapper
public interface FeedDAO
{
    /**
     * 将重复代码抽象出来
     * TABLE_NAME feed表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 增加 feed 流
     * @param feed
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    /**
     * 根据 id 查询 feed 流
     * @param id
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
