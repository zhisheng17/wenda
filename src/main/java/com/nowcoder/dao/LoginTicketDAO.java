package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by 10412 on 2016/7/2.
 * LoginTicketDAO
 */
@Mapper
public interface LoginTicketDAO {
    /**
     * 将重复代码抽象出来
     * TABLE_NAME login_ticket表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 增加 LoginTicket
     * @param ticket
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    /**
     * 查找 ticket
     * @param ticket
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * 更新 ticket
     * @param ticket
     * @param status
     */
    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
