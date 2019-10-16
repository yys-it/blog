package lhb.blog.com.dto;

import lombok.Data;

/**
 * @author lhb
 * @date 2019/10/11 10:52
 */
@Data
public class GitHubUser {
    private String name;
    private Integer id;
    private String bio;
    private String avatar_url;
}
