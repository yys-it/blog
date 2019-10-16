package lhb.blog.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.entity.Notification;
import lhb.blog.com.entity.Question;
import lhb.blog.com.entity.User;
import lhb.blog.com.enums.CommtentTypeEnum;
import lhb.blog.com.enums.NotificationStatusEunm;
import lhb.blog.com.enums.NotificationTypeEunm;
import lhb.blog.com.exception.CustomizeErrorCode;
import lhb.blog.com.exception.CustomizeException;
import lhb.blog.com.mapper.CommentMapper;
import lhb.blog.com.mapper.NotificationMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lhb
 * @date 2019/9/27 9:36
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    @Override
    public void insert(Comment comment, User user) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommtentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        if (comment.getType() == CommtentTypeEnum.COMMENT.getType()) {
            //回复评论
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            wrapper.eq("cid", comment.getParentId());
            Comment dbComment = commentMapper.selectOne(wrapper);
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
            }
            commentMapper.insert(comment);

            Notification notification = new Notification();
            notification.setNotifier(comment.getCommentator());
            notification.setRecevier(dbComment.getCommentator());
            notification.setOuterId(comment.getParentId());
            notification.setType(NotificationTypeEunm.COMMENT.getType());
            notification.setStatus(NotificationStatusEunm.UNREAD.getStatus());
            notification.setNotifierName(user.getUsername());
            notification.setContent(comment.getContent());
            notificationMapper.insert(notification);

        }else{
            //回复问题
            System.out.println("问题评论的问题");
            Question question = questionMapper.selectOne(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_PARAM_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.inComment(question.getId());

            Notification notification = new Notification();
            notification.setNotifier(comment.getCommentator());
            notification.setRecevier(question.getCreator());
            notification.setOuterId(question.getId());
            notification.setType(NotificationTypeEunm.QUESTION.getType());
            notification.setStatus(NotificationStatusEunm.UNREAD.getStatus());
            notification.setNotifierName(user.getUsername());
            notification.setContent(question.getTitle());
            notificationMapper.insert(notification);
        }
    }

    @Override
    public List<CommentDTO> selectByid(Integer id) {

        Integer type = CommtentTypeEnum.QUESTION.getType();
        List<CommentDTO> commentDTOS = commentMapper.selectCommentByParentId(id, type);
        return commentDTOS;
    }

    @Override
    public List<CommentDTO> selectByType(Integer id) {

        Integer type = CommtentTypeEnum.COMMENT.getType();
        List<CommentDTO> commentDTOS = commentMapper.selectCommentByParentId(id, type);
        return commentDTOS;
    }

}
