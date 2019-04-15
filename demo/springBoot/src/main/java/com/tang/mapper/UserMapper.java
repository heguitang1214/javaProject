package com.tang.mapper;

import com.tang.entry.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User getUserByName(String userName);

}
