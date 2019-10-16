package lhb.blog.com.enums;

/**
 * @author lhb
 * @date 2019/9/27 16:37
 */
public enum NotificationTypeEunm {
    QUESTION(1,"回复了一个问题"),
    COMMENT(2, "回复了一个评论"),
    ;

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    NotificationTypeEunm(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
