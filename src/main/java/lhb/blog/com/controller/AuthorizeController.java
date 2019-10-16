package lhb.blog.com.controller;

import lhb.blog.com.dto.AssessTokenDTO;
import lhb.blog.com.dto.GitHubUser;
import lhb.blog.com.entity.User;
import lhb.blog.com.entity.applicationValue;
import lhb.blog.com.mapper.UserMapper;
import lhb.blog.com.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.UUID;

/**
 * @author lhb
 * @date 2019/10/11 10:39
 */
@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;
    @Autowired
    UserMapper userMapper;
    @Autowired
    applicationValue applicationValue;


    @RequestMapping("/callBack")
    public String callBack(@RequestParam(value = "code")String code,
                           @RequestParam("state")String state, HttpSession session, HttpServletResponse response){
        System.out.println("code的值为"+code);


        AssessTokenDTO assessTokenDTO = new AssessTokenDTO();
        assessTokenDTO.setClient_id(applicationValue.getId());
        assessTokenDTO.setClient_secret(applicationValue.getSecret());
        assessTokenDTO.setCode(code);
        assessTokenDTO.setState(state);
        assessTokenDTO.setRedirect_uri(applicationValue.getUrl());
        String assessTokenDTO1 = gitHubProvider.getAssessTokenDTO(assessTokenDTO);
        System.out.println("出来了,token的值为"+assessTokenDTO1);
        GitHubUser user = gitHubProvider.getUser(assessTokenDTO1);
        System.out.println(user.getName());
        System.out.println(user.getId());
        System.out.println(user.getBio());
        System.out.println(user.getAvatar_url());
        User user1 = new User();
        if (user!=null){
            String token = UUID.randomUUID().toString();
            System.out.println("token的值为"+token);
            user1.setPassword(token);
            user1.setUsername(user.getName());
            user1.setAvatars(user.getAvatar_url());
            user1.setId(user.getId());
            User user2 = userMapper.selectUser(user1.getId());
            if (user2==null){
                userMapper.insert(user.getId(), user.getName(),token,user.getAvatar_url());
                response.addCookie(new Cookie("token", token));
                return "redirect:/";
            }
            response.addCookie(new Cookie("token", user2.getPassword()));

           // session.setAttribute("user",user1);
            return "redirect:/";
        }

        return "redirect:/";
    }

}
