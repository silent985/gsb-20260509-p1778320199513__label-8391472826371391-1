package com.staffsync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.staffsync.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM users WHERE TRIM(username) = #{username} LIMIT 1")
    User selectByUsernameTrim(@Param("username") String username);

    @Select("SELECT * FROM users WHERE TRIM(username) = #{username}")
    List<User> selectListByUsernameTrim(@Param("username") String username);

    @Select("SELECT COUNT(*) FROM users WHERE TRIM(username) = #{username}")
    int countByUsernameTrim(@Param("username") String username);
}
