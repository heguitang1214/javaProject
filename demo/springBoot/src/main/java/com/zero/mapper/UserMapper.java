package com.zero.mapper;

import com.zero.entry.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User getUserByName(String userName);

}
