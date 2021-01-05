package com.ween.swaggerdemo.controller;

import com.ween.swaggerdemo.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "测试接口")
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/getByName")
    @ApiOperation("根据用户姓名查询")
    @ApiImplicitParam(name = "name", value = "用户姓名", defaultValue = "testUser", required = true)
    public Person getPersonByName(@RequestParam String name) {
        Person p = new Person();
        p.setFullname("testUser");
        p.setPassword("testUser-pwd");
        return p;
    }

    @PutMapping("/changePassword")
    @ApiOperation("根据用户姓名修改密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户姓名", defaultValue = "testUser", required = true)
            , @ApiImplicitParam(name = "fresh", value = "新密码", defaultValue = "123456", required = true)})
    public Person getPersonByName(@RequestParam String name, @RequestParam String fresh) {
        Person p = new Person();
        p.setFullname(name);
        p.setPassword(fresh);
        return p;
    }
}
