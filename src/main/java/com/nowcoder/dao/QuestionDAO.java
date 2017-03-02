package com.nowcoder.dao;

import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 10412 on 2016/7/2.
 * Mybatis使用注解的配置方式，用接口加注解来实现 问题 的CRUD
 */
@Mapper
public interface QuestionDAO {
    /**
     * 将重复代码抽象出来
     * TABLE_NAME  question表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 增加提问
     * @param question
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);


    /**
     * 根据 id 查询所提的问题
     * @param id
     * @return 问题
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question getById(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

    /**
     * 根据 id 更新用户所提问题的评论数量
     * @param id
     * @param commentCount
     * @return 评论数量
     */
    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

}
