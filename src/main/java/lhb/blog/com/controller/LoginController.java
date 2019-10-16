package lhb.blog.com.controller;

import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.UserMapper;
import lhb.blog.com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/logins",method = RequestMethod.GET)
    public String tologin(){
        return "login";
    }

    @RequestMapping(value = "/registers",method = RequestMethod.GET)
    public String toregister(){
        return "register";
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(@RequestParam(value = "username",required = false) String username,
                        @RequestParam(value = "password",required = false) String password,
                        Model model, HttpSession session,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "6")Integer size,
                        @RequestParam(value = "search",required = false)String search) {


        if (username == "" || username == null) {
            model.addAttribute("msg", "输入账户不能为空");
            return "login";
        }
        if (password == "" || password == null) {
            model.addAttribute("msg", "输入密码不能为空");
            return "login";
        }
        if (username.length()<3 || username.length()>10) {
            model.addAttribute("msg", "登录失败，账户长度在3~10个字符之间");
            return "login";
        }
        if (password.length() < 3 || password.length() > 15) {
            model.addAttribute("msg","登录失败，密码长度在3~15个字符之间");
            return "login";
        }

        if (userMapper.selectUserByName(username) == 0) {
            model.addAttribute("msg","登录失败，账户不存在");
            return "login";
        }

        if (userMapper.selectByNameAndPass(username, password) == 0) {
            model.addAttribute("username", username);
            model.addAttribute("msg","登录失败，密码错误");
            return "login";
        }

        User user = userMapper.selectUserByNameAndPass(username, password);
        session.setAttribute("user", user);

        PageDTO pageQuestionDTO = questionService.list(page, size,search);
        model.addAttribute("pageQuestionDTO", pageQuestionDTO);

        return "index";
    }


    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public String register(@RequestParam(value = "username",required = false) String username,
                           @RequestParam(value = "password",required = false) String password,
                           Model model){

        if (username == "" || username == null) {
            model.addAttribute("msg", "输入账户不能为空");
            return "register";
        }
        if (password == "" || password == null) {
            model.addAttribute("msg", "输入密码不能为空");
            return "register";
        }
        if (username.length()<3 || username.length()>10) {
            model.addAttribute("msg", "注册失败，账户长度在3~10个字符之间");
            return "register";
        }
        if (password.length() < 3 || password.length() > 15) {
            model.addAttribute("msg","注册失败，密码长度在3~15个字符之间");
            return "register";
        }

        if (userMapper.selectUserByName(username) == 1) {
            model.addAttribute("msg","注册失败，账户已存在");
            return "register";
        }


        String avatars = "https://i0.hdslb.com/bfs/archive/b49ad8086bab3ee726150d02aa2712e29db143e2.jpg@880w_440h.jpg";


        userMapper.insert(null,username,password,avatars);

        return "login";
    }



}
