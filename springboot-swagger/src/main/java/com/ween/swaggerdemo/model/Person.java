package com.ween.swaggerdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户实体类")
public class Person {
    @ApiModelProperty(value = "用户姓名")
    private String fullname;
    @ApiModelProperty(value = "用户密码")
    private String password;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
