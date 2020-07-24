package com.kakarot.controller;

import com.kakarot.pojo.Users;
import com.kakarot.pojo.bo.UserBO;
import com.kakarot.service.UsersService;
import com.kakarot.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){

        //1、判断用户名是否为空,null、""、"  "、"\n"都为空；用StringUtils可以避免出现NPE
        if(StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }

        //2、查找注册的用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(username);
        if(isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        //3、请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO){

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        //1、判断用户名、密码必须不为空
        if(StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //2、查询用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(username);
        if(isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        //3、密码长度不能少于6位
        if(password.length()<6){
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }

        //4、判断两次密码是否一致
        if(!password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次输入密码不一致");
        }

        //5、实现注册
        Users userResult = usersService.createUser(userBO);

        return IMOOCJSONResult.ok();
    }

}
