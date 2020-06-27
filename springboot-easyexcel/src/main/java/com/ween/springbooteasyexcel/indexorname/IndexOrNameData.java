package com.ween.springbooteasyexcel.indexorname;

import com.alibaba.excel.annotation.ExcelProperty;

public class IndexOrNameData {

    //绑定列索引
    @ExcelProperty(index = 2)
    private String password;
    //绑定列名称
    @ExcelProperty(value = "用户名")
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
