package lhb.blog.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lhb.blog.com.cache.TagCache;
import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.entity.Notification;
import lhb.blog.com.entity.Question;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.CommentMapper;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.service.CommentService;
import lhb.blog.com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @RequestMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        QuestionDTO questionDTO = questionService.selectByid(id);

        List<Question> questions = questionService.selectInQuestion(questionDTO.getId());
        List<CommentDTO> commentDTOS = commentService.selectByid(id);


        questionService.inViewCount(id);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentDTOS", commentDTOS);
        model.addAttribute("questions", questions);
        return "question";
    }

    /*@RequestMapping("/question/delete/{id}")
    public String delete(@PathVariable("id")Integer id, Model model, HttpSession session){

       *//* questionMapper.deleteById(id);



        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
        QueryWrapper<Comment> wrapper3 = new QueryWrapper<>();
        wrapper.eq("type", 1);
        wrapper.eq("parent_id", id);
        List<Comment> comments = commentMapper.selectList(wrapper);

        commentMapper.delete(wrapper);

        User user = (User) session.getAttribute("user");
        model.addAttribute("section","questions");
        model.addAttribute("sectionName","问题列表");
        PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
        pageQuestionDTO = null;
        try {
            Integer count = questionMapper.myQuestionCount(user.getId());
            pageQuestionDTO= questionService.selectMyQuestion(user.getId(), 1, 5);
            model.addAttribute("pageQuestionDTO", pageQuestionDTO);
            model.addAttribute("count",count);
        } catch (Exception e) {
        }*//*

        QuestionDTO questionDTO = questionService.selectByid(id);
        int i = questionService.deleteQuestion(questionDTO);

        User user = (User) session.getAttribute("user");
        model.addAttribute("section","questions");
        model.addAttribute("sectionName","问题列表");
        PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
        pageQuestionDTO = null;
        try {
            Integer count = questionMapper.myQuestionCount(user.getId());
            pageQuestionDTO= questionService.selectMyQuestion(user.getId(), 1, 5);
            model.addAttribute("pageQuestionDTO", pageQuestionDTO);
            model.addAttribute("count",count);
        } catch (Exception e) {
        }

        return "profile";

    }*/

}
