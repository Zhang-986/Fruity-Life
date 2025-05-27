package com.fruit.mapper;

import com.fruit.entity.po.GuestSessions;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Zhang-986
* @description 针对表【guest_sessions(存储游客临时会话信息，用于个性化推荐和BMI计算)】的数据库操作Mapper
* @createDate 2025-05-24 13:35:51
* @Entity com.fruit.entity.po.GuestSessions
*/
@Mapper
public interface GuestSessionsMapper {

    void insert(GuestSessions entity);

    GuestSessions selectByEmailAndPassword(String email, String password);

    void insertEntity(GuestSessions entity);

    GuestSessions verifyEmail(String email);

    void updatePasswordByEmail(String email, String password);
}




