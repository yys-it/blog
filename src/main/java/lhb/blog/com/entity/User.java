package lhb.blog.com.entity;

import lombok.Data;

@Data
public class User{

    private Integer id;
    private String username;
    private String password;
    private String avatars;  //用户头像
    private String token;
}
