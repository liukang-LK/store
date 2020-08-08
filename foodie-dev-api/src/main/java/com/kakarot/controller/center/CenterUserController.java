package com.kakarot.controller.center;

import com.kakarot.pojo.Users;
import com.kakarot.pojo.bo.center.CenterUserBO;
import com.kakarot.service.center.CenterUserService;
import com.kakarot.utils.CookieUtils;
import com.kakarot.utils.IMOOCJSONResult;
import com.kakarot.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    /**
     *
     * @param userId
     * @param centerUserBO
     * @param result 验证结果集
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "修改用户信息",notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response){

        System.out.println(centerUserBO);

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if(result.hasErrors()){
            Map<Object, Object> errorMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }

        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);

        userResult=setNullProperty(userResult);

        //设置cookie
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话

        return IMOOCJSONResult.ok();

    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<Object, Object> getErrors(BindingResult result){
        Map<Object, Object> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for(FieldError error:errorList){
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField,errorMsg);
        }
        return map;
    }

}
