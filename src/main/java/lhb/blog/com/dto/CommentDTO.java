package lhb.blog.com.dto;

import lhb.blog.com.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * @author lhb
 * @date 2019/9/29 16:46
 */
@Data
public class CommentDTO {
    private Integer cid;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Date createTime;
    private Date updateTime;
    private Integer likeCount;
    private String content;
    private User user;
}
