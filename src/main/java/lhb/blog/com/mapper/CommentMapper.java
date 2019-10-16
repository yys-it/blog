package lhb.blog.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lhb.blog.com.dto.CommentDTO;
import lhb.blog.com.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lhb
 * @date 2019/9/27 16:58
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

     List<CommentDTO> selectCommentByParentId(Integer parentId,Integer type);

     CommentDTO selectOneCommentById(Integer id);

}
