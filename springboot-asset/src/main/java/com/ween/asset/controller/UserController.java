package com.ween.asset.controller;

import com.ween.asset.entity.Person;
import com.ween.asset.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by wen on 2018/8/21
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        String usercode = request.getParameter("usercode");
        String password = request.getParameter("password");
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(usercode, password.toCharArray());
        try {
            user.login(token);
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "*用户名不存在");
            return "index";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "*密码错误");
            return "index";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg", "*用户名被锁定,请与管理员联系");
            return "index";
        } catch (ExcessiveAttemptsException eae) {
            model.addAttribute("msg", "*尝试登录数次超过限次,用户名锁定");
            return "index";

        } catch (AuthenticationException e) {
            model.addAttribute("msg", "*未知错误，请与管理员联系");
            return "index";
        }
        model.addAttribute("uname", usercode);
        return "user/zc_manage";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        SecurityUtils.getSubject().logout();
        return "index";
    }

    @RequestMapping("/manage")
    public String manage(Model model) {
        return "user/zc_manage";
    }

    @GetMapping("/userinfo")
    public String userinfo(Model model) {
        return "user/user_info";
    }

    @GetMapping("/zcalter")
    public String zcalter(Model model) {
        return "user/zc_alter";
    }

    @GetMapping("/zcinput")
    public String zcinput(Model model) {
        return "user/zc_input";
    }

    @GetMapping("/zcsearch")
    public String zcsearch(Model model) {
        return "user/zc_search";
    }

    @GetMapping("/zcuse")
    public String zcuser(Model model) {
        return "user/zc_use";
    }

    @RequestMapping(value = "/list")
    public String list(Model model) {
        List<Person> list = userRepository.findAll();
        model.addAttribute("list", list);
        return "list";
    }
}
