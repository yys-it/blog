package lhb.blog.com.dto;

import lhb.blog.com.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer notifier; //回复者
    private Integer recevier; //接受者
    private Integer outerId;
    private Integer type;
    private Date createTime;
    private Integer status; //状态，已看或者未看
    private String notifierName; //回复者的名字
    private String recevierContent;  //回复内容，如果是问题则显示问题，如果是评论则显示评论
    private User user;
    private Integer count;
}
