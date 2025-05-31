package com.fruit.service;

import com.fruit.entity.dto.GuestSessionsDTO;
import com.fruit.entity.po.GuestSessions;
import com.fruit.entity.vo.GuestSessionsVo;
import com.fruit.result.R;

public interface IGuestSessions {

    R<String> register(GuestSessionsDTO guestSessions);

    R<String> getCode(String email);

    R<String> login(GuestSessionsDTO guestSessions);

    R<Boolean> verifyEmail(String email);

    R<String> handlePassword(GuestSessionsDTO guestSessions);

//    Boolean isCompleted();

    R<String> completeProfile(GuestSessionsDTO guestSessions);

    R<GuestSessionsVo> getEntityById(Long userId);
}
