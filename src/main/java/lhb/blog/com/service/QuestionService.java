package lhb.blog.com.service;

import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Question;

import java.util.List;

public interface QuestionService {
    void insertOrUpdateQuestion(String title,String description,String tag,Integer id,Integer userId);
    PageDTO<QuestionDTO> list(Integer page, Integer size,String search);

    QuestionDTO selectByid(Integer id);

    void inViewCount(Integer id);

    List<Question> selectInQuestion(Integer id);

    PageDTO<QuestionDTO> selectMyQuestion(Integer id, Integer page, Integer size);

    int deleteQuestion(QuestionDTO questionDTO);
}
