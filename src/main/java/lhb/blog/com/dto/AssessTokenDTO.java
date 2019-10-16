package lhb.blog.com.dto;

import lombok.Data;

/**
 * @author lhb
 * @date 2019/10/11 10:08
 */
@Data
public class AssessTokenDTO {
    private String client_id;
    private String redirect_uri;
    private String state;
    private String client_secret;
    private String code;
}
