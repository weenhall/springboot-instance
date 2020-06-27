package com.ween.springbooteasyexcel.demo;

import com.alibaba.excel.annotation.ExcelProperty;

public class DemoData {

    @ExcelProperty("用户名")
    private String user;
    @ExcelProperty("密码")
    private String pwd;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
