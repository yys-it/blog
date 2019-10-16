package lhb.blog.com.service;

import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.entity.User;

import java.util.List;

/**
 * @author lhb
 * @date 2019/9/27 9:35
 */
public interface CommentService {

    void insert(Comment comment, User user);

    List<CommentDTO> selectByid(Integer id);

    List<CommentDTO> selectByType(Integer id);
}
