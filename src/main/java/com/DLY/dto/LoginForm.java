package com.DLY.dto;

import lombok.Data;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.dto
 * @className: LoginForm
 * @author: DaiLinYu
 * @date: 2022/4/19 21:08
 * @version: 1.0
 * @description: 登陆验证类
 */
@Data
public class LoginForm {
    //用户名
    private String username;
    //密码
    private String password;
    //验证码
    private String verifiCode;
    //登陆人员分类
    private Integer userType;
}
