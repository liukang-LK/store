package com.kakarot.service;

import com.kakarot.pojo.Users;
import com.kakarot.pojo.bo.UserBO;

public interface UsersService {

    /**
     * 判断数据库是否存在此username
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    public Users createUser(UserBO userBO);

}
