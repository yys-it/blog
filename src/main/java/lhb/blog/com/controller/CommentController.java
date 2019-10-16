package lhb.blog.com.controller;

import lhb.blog.com.dto.CommentCreateDTO;
import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.dto.ResultDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.entity.User;
import lhb.blog.com.exception.CustomizeErrorCode;
import lhb.blog.com.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody(required = false) CommentCreateDTO commentDTO, HttpServletRequest request, HttpSession session) {

        User user = (User) session.getAttribute("user");


        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        if (commentDTO == null || StringUtils.isAllBlank(commentDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setCommentator(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }


    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable("id") Integer id) {

        List<CommentDTO> commentDTOS = commentService.selectByType(id);
        ResultDTO<List<CommentDTO>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(commentDTOS);
        return resultDTO;
    }

}
