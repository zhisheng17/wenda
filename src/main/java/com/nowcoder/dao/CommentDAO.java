package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 10412 on 2016/7/2.
 * Mybatis使用注解的配置方式，用接口加注解来实现 评论 的CRUD
 */
@Mapper
public interface CommentDAO
{
    /**
     * 将重复代码抽象出来
     * TABLE_NAME 评论表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 插入评论
     * @param comment
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    /**
     * 根据id查询评论
     * @param id
     * @return 评论信息
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{Id}"})
    Comment getCommentById(int id);

    /**
     * 根据id更新评论的状态（1代表删除，0代表存在）
     * @param id
     * @param status
     * @return 评论的状态
     */
    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id,  @Param("status") int status);


    /**
     * 根据 entityId 和 entityType 查询评论并按时间降序排序
     * @param entityId
     * @param entityType
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);


    /**
     * 根据 entityId 和 entityType 查询评论，并获取评论数量
     * @param entityId
     * @param entityType
     * @return 评论数量
     */
    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);


    /**
     * 根据 userId 查询用户评论的数量
     * @param userId
     * @return 用户评论的数量
     */
    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);
}
