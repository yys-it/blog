package lhb.blog.com.controller;

import lhb.blog.com.cache.TagCache;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.dto.TagDTO;
import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationMapper notificationMapper;


    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        List<TagDTO> tagDTOS = TagCache.get();
        for (TagDTO t:tagDTOS
             ) {
            System.out.println(t.toString());
        }
        return "publish";
    }

    @RequestMapping("/publish/{id}")
    public String update(@PathVariable(value = "id",required = false) Integer id,
                         Model model){
        QuestionDTO questionDTO = questionService.selectByid(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }


    @RequestMapping(value = "publish",method = RequestMethod.POST)
    public String insertquestion(@RequestParam(value = "title",required = false) String title,
                                 @RequestParam(value = "description",required = false)String description,
                                 @RequestParam(value = "tag",required = false)String tag,
                                 @RequestParam(value = "id",required = false)Integer id,
                                 @RequestParam(value = "search",required = false)String  search,Model model, HttpSession session){

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags",TagCache.get());

        User user = (User) session.getAttribute("user");
        //System.out.println(user.toString());
        if (user == null) {
            model.addAttribute("msg","你还没有登录，请先登录");
            System.out.println("你还没有登录，请先登录");
            return "publish";
        }


        if (title == null || title == ""){
            model.addAttribute("msg","标题不能为空");
            System.out.println("标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("msg","问题补充不能为空");
            System.out.println("问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("msg","标签不能为空");
            System.out.println("标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)){
            model.addAttribute("msg", "输入标签不合法：" + invalid);
            return "publish";
        }



        questionService.insertOrUpdateQuestion(title, description,tag,id,user.getId());

        int count = 0;
        if (user != null){
            System.out.println("到这里");
            count = notificationMapper.navificationUnreadCount(user.getId());
        }
        model.addAttribute("count", count);

        PageDTO pageQuestionDTO = questionService.list(1, 6,search);
        model.addAttribute("pageQuestionDTO", pageQuestionDTO);
        model.addAttribute("tags",TagCache.get());
        return "index";
    }
}
