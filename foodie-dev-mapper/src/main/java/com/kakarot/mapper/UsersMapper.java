package com.kakarot.mapper;

import com.kakarot.my.mapper.MyMapper;
import com.kakarot.pojo.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {
}