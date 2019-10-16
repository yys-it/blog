package lhb.blog.com.controller;

import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.mapper.UserMapper;
import lhb.blog.com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLSyntaxErrorException;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationMapper notificationMapper;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "10")Integer size,
                        @RequestParam(value = "search",required = false)String  search,
                        HttpSession session, HttpServletRequest request){

        System.out.println(search);

        User user = null;

        user = (User) session.getAttribute("user");

        Cookie[] cookies = request.getCookies();
        try {
            for (Cookie c:cookies
            ) {
                System.out.println(c.getName());
                if (c.getName().equals("token")){
                    String token = c.getValue();
                    User user1 = userMapper.selectUserByPass(token);
                    if (user1 != null) {
                        session.setAttribute("user",user1);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nncount = 0;
        if (user != null){
            nncount = notificationMapper.navificationUnreadCount(user.getId());
        }
        model.addAttribute("nncount", nncount);
        PageDTO pageQuestionDTO = null;
        try {
            pageQuestionDTO = questionService.list(page, size, search);
        } catch (Exception e) {
            System.out.println("出问题了");
        }
        model.addAttribute("search", search);
        model.addAttribute("pageQuestionDTO", pageQuestionDTO);
        return "index";
    }

    @RequestMapping("/laygout")
    public String laygout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies
        ) {
            System.out.println(c.getName());
            if (c.getName().equals("token")){
                Cookie newCookie=new Cookie("token",null); //假如要删除名称为username的Cookie

                newCookie.setMaxAge(0); //立即删除型

                newCookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除

                response.addCookie(newCookie); //重新写入，将覆盖之前的
            }

        }

        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/getsession")
    public String getsession(HttpSession session,Model model,
                             @RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "6")Integer size,
                             @RequestParam(value = "search",required = false)String  search){

        User user = userMapper.selectUser(3);
        session.setAttribute("user",user);
        User user1 = (User) session.getAttribute("user");
        //System.out.println("user的session值为："+user1.toString());
        int nncount = 0;
        if (user != null){
            nncount = notificationMapper.navificationUnreadCount(user1.getId());
        }

        model.addAttribute("nncount", nncount);

        System.out.println(user1.toString());
        PageDTO<QuestionDTO> pageQuestionDTO = questionService.list(page, size,search);
        model.addAttribute("search", search);
        model.addAttribute("pageQuestionDTO", pageQuestionDTO);
        return "index";
    }
}
