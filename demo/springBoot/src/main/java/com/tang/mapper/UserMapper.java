package com.tang.mapper;

import com.tang.entry.UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    UserInfo getUserByName(String userName);

}
