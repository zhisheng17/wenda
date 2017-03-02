package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by 10412 on 2016/7/2.
 * Mybatis使用注解的配置方式，用接口加注解来实现 用户 的CRUD
 */
@Mapper
public interface UserDAO {
    /**
     * 将重复代码抽象出来
     * TABLE_NAME 用户表
     * INSERT_FIELDS 属性
     * SELECT_FIELDS 所选择属性
     */
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";

    /**
     * 插入用户
     * @param user
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS, ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    /**
     * 根据 id 查询用户
     * @param id
     * @return 用户信息
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    /**
     * 根据 name 查询用户
     * @param name
     * @return 用户信息
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    /**
     * 根据用户 id 更改用户登录密码
     * @param user
     */
    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    /**
     * 根据用户 id 删除用户
     * @param id
     */
    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
