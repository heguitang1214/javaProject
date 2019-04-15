package com.tang.security;

import com.tang.entry.User;
import com.tang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userinfo = userMapper.getUserByName(username);
        return new org.springframework.security.core.userdetails.User(username, userinfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userinfo.getRole()));
    }

}
