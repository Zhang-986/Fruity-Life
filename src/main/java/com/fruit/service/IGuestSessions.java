package com.fruit.service;

import com.fruit.entity.dto.GuestSessionsDTO;
import com.fruit.result.R;

public interface IGuestSessions {

    R<String> register(GuestSessionsDTO guestSessions);

    R<String> getCode(String email);
}
