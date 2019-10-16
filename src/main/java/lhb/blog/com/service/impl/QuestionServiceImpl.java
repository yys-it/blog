package lhb.blog.com.service.impl;

import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.dto.PageDTO;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Question;
import lhb.blog.com.entity.User;
import lhb.blog.com.exception.CustomizeErrorCode;
import lhb.blog.com.exception.CustomizeException;
import lhb.blog.com.mapper.CommentMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.mapper.UserMapper;
import lhb.blog.com.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void insertOrUpdateQuestion(String title, String description, String tag,Integer id,Integer userId) {

        if (id == null) {
            int i = questionMapper.insertQuestion(title, description, tag,userId);
            if (i > 0) {
                System.out.println("添加成功");
            }
        }else {
            User user = userMapper.selectUser(userId);
            QuestionDTO questionDTO = questionMapper.selectById(id);
            if (user.getId() == questionDTO.getCreator()){
                int update = questionMapper.update(title, description, tag, id);
                if (update != 1) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
            }else {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_USER_NOT_EQUL);
            }

        }

    }

    @Override
    public PageDTO<QuestionDTO> list(Integer page, Integer size,String search) {

        if (search!=null && search!="") {
            System.out.println("zhellile");

            String[] tags = StringUtils.split(search, " ");
            String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
            System.out.println("regexp的值为"+regexpTag);

            Integer count = questionMapper.countLikeBySearch(regexpTag);
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



            Map<String, Object> map = new HashMap<>();
            //map.put("search", search);
            map.put("search", regexpTag);
            map.put("page", (page - 1) * size);
            map.put("size", size);

           // List<QuestionDTO> questionDTOS = questionMapper.selectLikeBySearch(search, (page - 1) * size, size);
            System.out.println(count);
            List<QuestionDTO> questionDTOS = questionMapper.selectLikeBySearch(map);
            System.out.println("hello");
            PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
            pageQuestionDTO.setData(questionDTOS);
            pageQuestionDTO.setPagination(count,page,size);
            return pageQuestionDTO;
        }else {
            Integer count = questionMapper.count();
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

            List<QuestionDTO> questionDTOS = questionMapper.list((page - 1) * size, size);
            PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
            pageQuestionDTO.setData(questionDTOS);
            pageQuestionDTO.setPagination(count,page,size);
            return pageQuestionDTO;
        }



    }

    @Override
    public QuestionDTO selectByid(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO= questionMapper.selectById(id);
        System.out.println(questionDTO.toString());
        if (questionDTO == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return questionDTO;
    }

    @Override
    public void inViewCount(Integer id) {
        QuestionDTO questionDTO = questionMapper.selectById(id);
        questionMapper.inView(id,questionDTO.getViewCount());
    }

    @Override
    public List<Question> selectInQuestion(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO= questionMapper.selectById(id);
        if (questionDTO == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Question question = new Question();
        question.setId(questionDTO.getId());

        String[] tags = StringUtils.split(questionDTO.getTag(), ',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectInQuestion(question);

        return questions;
    }

    @Override
    public PageDTO<QuestionDTO> selectMyQuestion(Integer id, Integer page, Integer size) {
        Integer count = questionMapper.myQuestionCount(id);
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

        List<QuestionDTO> questionDTOS = questionMapper.selectMyQuestion(id,(page - 1) * size, size);
        PageDTO<QuestionDTO> pageQuestionDTO = new PageDTO();
        pageQuestionDTO.setData(questionDTOS);
        pageQuestionDTO.setPagination(count,page,size);
        return pageQuestionDTO;
    }

    @Override
    public int deleteQuestion(QuestionDTO questionDTO) {

        //一级评论
        List<CommentDTO> commentDTOS = commentMapper.selectCommentByParentId(questionDTO.getId(), 1);

        return 0;
    }

}
