package com.fruit.service;

import com.fruit.entity.GuestSessions;
import com.fruit.result.R;

public interface IGuestSessions {

    void register(GuestSessions guestSessions);

    R<String> getCode();
}
