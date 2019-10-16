package lhb.blog.com.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {

    private Integer id;
    private Integer notifier; //回复者
    private Integer recevier; //接受者
    private Integer outerId;
    private Integer type;
    private Date createTime;
    private Integer status; //状态，已看或者未看
    private String notifierName;
    private String content;
}

