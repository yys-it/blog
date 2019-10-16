package lhb.blog.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.dto.NotificationDTO;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.entity.Question;
import lhb.blog.com.entity.User;
import lhb.blog.com.enums.NotificationTypeEunm;
import lhb.blog.com.exception.CustomizeErrorCode;
import lhb.blog.com.exception.CustomizeException;
import lhb.blog.com.mapper.CommentMapper;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageDTO<NotificationDTO> selectById(Integer userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();

        Integer count = notificationMapper.count(userId);
        Integer totalPage;
        if (page == 0){
            page = 1;
        }
        if (count % size == 0){
            totalPage = count / size;
        }else {
            totalPage = count / size + 1;
        }
        if (page > totalPage){
            page = totalPage;
        }


        List<NotificationDTO> notificationDTOS = notificationMapper.selectById(userId,(page - 1) * size, size);
        pageDTO.setData(notificationDTOS);
        pageDTO.setPagination(count, page, size);

        return pageDTO;
    }

    @Override
    public QuestionDTO selectQuestionId(Integer id, User user) {

        QuestionDTO questionDTO = new QuestionDTO();

        NotificationDTO notificationDTO = notificationMapper.selectOneById(id);
        if (notificationDTO == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!user.getId().equals(notificationDTO.getRecevier())) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_USER_NOT_EQUL);
        }



        if (notificationDTO.getType().equals(NotificationTypeEunm.QUESTION.getType())) {
            questionDTO = questionMapper.selectById(notificationDTO.getOuterId());
        }else if (notificationDTO.getType().equals(NotificationTypeEunm.COMMENT.getType())) {
            System.out.println(notificationDTO.toString());
            System.out.println("执行前");
            CommentDTO commentDTO = commentMapper.selectOneCommentById(notificationDTO.getOuterId());
            questionDTO = questionMapper.selectById(commentDTO.getParentId());
            System.out.println("questionDTO:"+questionDTO.toString());
        }

        notificationMapper.updateStatus(1, notificationDTO.getId());

        return questionDTO;
    }
}
