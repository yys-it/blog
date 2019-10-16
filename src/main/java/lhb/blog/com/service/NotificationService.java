package lhb.blog.com.service;

import lhb.blog.com.dto.NotificationDTO;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Question;
import lhb.blog.com.entity.User;

public interface NotificationService {
    PageDTO<NotificationDTO> selectById(Integer userId, Integer page, Integer id);

    QuestionDTO selectQuestionId(Integer id, User user);
}
