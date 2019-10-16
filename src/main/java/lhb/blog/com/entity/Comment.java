package lhb.blog.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer cid;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Date createTime;
    private Date updateTime;
    private Integer likeCount;
    private String content;
}
