package com.kakarot.service.impl;

import com.kakarot.enums.Sex;
import com.kakarot.mapper.UsersMapper;
import com.kakarot.pojo.Users;
import com.kakarot.pojo.bo.UserBO;
import com.kakarot.service.UsersService;
import com.kakarot.utils.DateUtil;
import com.kakarot.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    
    @Autowired
    private Sid sid;

    private static final String USER_FACE = "https://imgur.com/QZ2e0Mt";

    /**
     * 判断数据库是否存在此username
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        //1.example用来放一些去重，排序，分类，分页等信息
        Example userExample = new Example(Users.class);
        //2.criteria用来传字段参数
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username",username);

        Users result = usersMapper.selectOneByExample(userExample);

        return result==null?false:true;

    }

    /**
     * 创建用户
     * @param userBO
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String userId = sid.nextShort();

        Users user = new Users();
        //id不是自增的，是根据工具类自动生成的16位字符串
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        //密码MD5加密存储
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认性别为 保密
        user.setSex(Sex.secret.type);
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);
        return user;
    }

}
