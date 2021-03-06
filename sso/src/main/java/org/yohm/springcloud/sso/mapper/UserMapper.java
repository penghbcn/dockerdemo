package org.yohm.springcloud.sso.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yohm.springcloud.sso.model.User;

/**
 * 功能简述
 * (用户相关持久层)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
public interface UserMapper {
    /**
     * 新增用户
     * @param user
     * @return
     */
    @Insert("insert into demo_user_t " +
            "values " +
            "(null," +
            "#{user.username}," +
            "#{user.password}," +
            "#{user.age}," +
            "#{user.phone}," +
            "#{user.email})")
    int insertOne(@Param("user")User user);

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    @Select("<script>" +
            "select " +
            "username," +
            "password," +
            "age," +
            "phone," +
            "email " +
            "from demo_user_t " +
            "<where>" +
            "<if test='username != null'>" +
            "and username=#{username} " +
            "</if>" +
            "<if test='password != null'>" +
            "and password=#{password} " +
            "</if>" +
            "</where>" +
            "</script>")
    User selectOneByUserNameAndPassword(@Param("username") String username, @Param("password")String password);
}
