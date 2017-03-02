package com.nowcoder.dao;

import com.nowcoder.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 10412 on 2016/7/2.
 * Mybatis使用注解的配置方式，用接口加注解来实现 消息 的CRUD
 */
@Mapper
public interface MessageDAO
{
    /**
     * 将重复代码抽象出来
     * TABLE_NAME 消息表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 发送私信
     * @param message
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    /**
     * 根据私信id更新私信是否查看的状态
     * @param id
     * @param status
     * @return
     */
    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 根据conversationId获取私信详情，默认按时间降序
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取评论的数量
     * @param entityId
     * @param entityType
     * @return 评论数量
     */
    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);


    /**
     * 根据发送者和接收者查询私信消息列表，默认按照时间降序排列
     * @param userId
     * @param offset
     * @param limit
     * @return 私信消息列表
     */
    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id desc order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);


    /**
     * 查询私信消息未读数量
     * @param userId
     * @param conversationId
     * @return 私信消息未读数量
     */
    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

}
