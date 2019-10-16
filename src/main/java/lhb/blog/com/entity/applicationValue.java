package lhb.blog.com.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lhb
 * @date 2019/10/11 15:25
 */
@Component
@ConfigurationProperties(prefix = "github-client")
@Data
public class applicationValue {
    private String id;
    private String url;
    private String secret;
}
