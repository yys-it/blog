package lhb.blog.com.controller;

import lhb.blog.com.dto.NotificationDTO;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.service.NotificationService;
import lhb.blog.com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    QuestionMapper questionMapper;

    @RequestMapping("profile/{action}")
    public String profile(@PathVariable("action")String action, Model model,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "5")Integer size, HttpSession session){

        User user = (User) session.getAttribute("user");


        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","问题列表");

            PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
            pageQuestionDTO = null;
            try {
                Integer count = questionMapper.myQuestionCount(user.getId());
                pageQuestionDTO= questionService.selectMyQuestion(user.getId(), page, size);
                model.addAttribute("pageQuestionDTO", pageQuestionDTO);
                model.addAttribute("count",count);
            } catch (Exception e) {
            }


        }
        if ("repies".equals(action)){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","回复列表");
            PageDTO<NotificationDTO> pageDTOs = new PageDTO<>();
           int count = 0;
            try {
                pageDTOs = notificationService.selectById(user.getId(),page, size);
                count = notificationMapper.navificationUnreadCount(user.getId());
                System.out.println(count);
                model.addAttribute("count",count);
                model.addAttribute("notificationDTOS", pageDTOs);
            } catch (Exception e) {
                System.out.println("系统异常");
            }
        }


        return "profile";
    }
}
