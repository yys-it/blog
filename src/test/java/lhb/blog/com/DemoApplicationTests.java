package lhb.blog.com;

import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Comment;
import lhb.blog.com.mapper.CommentMapper;
import lhb.blog.com.mapper.QuestionMapper;
import lhb.blog.com.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentMapper commentMapper;

    @Test
    public void contextLoads() {
        /*for (int i=0;i<=15;i++){
            questionMapper.insertQuestion("标题"+i,"补充内容"+i,"标签"+i);
        }*/

        /*List<QuestionDTO> list = questionMapper.list(1, 2);
        for (QuestionDTO l:list
             ) {
            System.out.println(l.toString());
        }*/
        Comment comment = new Comment();

        /*comment.setType(2);
        comment.setCommentator(4);
        comment.setParentId(1);
        comment.setContent("fasd");
        commentMapper.insert(comment);*/

       /* QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",1);
       // Comment dbComment = commentMapper.selectOne(wrapper);
        List<Comment> comments = commentMapper.selectList(wrapper);
        for (Comment x:comments
             ) {
            System.out.println(x.toString());
        }*/


        /*Integer type = CommtentTypeEnum.QUESTION.getType();
        List<CommentDTO> commentDTOS = commentMapper.selectCommentByParentId(1, type);
        if (commentDTOS != null) {
            int i =0;
            for (CommentDTO c:commentDTOS
                 ) {
                System.out.println(c.toString());
                i=i+1;
            }
            System.out.println(i);
        }*/

        List<QuestionDTO> questionDTOS = questionMapper.list(1, 2);
        for (QuestionDTO q:questionDTOS
        ) {
            System.out.println(q.toString());
        }

        //commentMapper.insertComment(comment);



    }

}
