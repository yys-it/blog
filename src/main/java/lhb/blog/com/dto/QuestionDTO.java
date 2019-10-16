package lhb.blog.com.dto;

import lhb.blog.com.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Date createTime;
    private User user;
}
