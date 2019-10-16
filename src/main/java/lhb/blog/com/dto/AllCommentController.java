package lhb.blog.com.dto;

import lhb.blog.com.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lhb
 * @date 2019/10/12 11:23
 */
@Data
public class AllCommentController {
    private Integer cid;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Date createTime;
    private Date updateTime;
    private Integer likeCount;
    private String content;
    private User user;

    List<CommentDTO> commentDTOList;    //二级评论

}
