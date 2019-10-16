package lhb.blog.com.controller;


import lhb.blog.com.dto.NotificationDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationMapper notificationMapper;

    @RequestMapping("/notification/{id}")
    public String notification(@PathVariable("id")Integer id, HttpSession session){

        User user = (User) session.getAttribute("user");

        QuestionDTO questionDTO = notificationService.selectQuestionId(id, user);


        return "redirect:/question/"+questionDTO.getId();
    }

}
