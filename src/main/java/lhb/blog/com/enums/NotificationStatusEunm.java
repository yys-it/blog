package lhb.blog.com.enums;

/**
 * @author lhb
 * @date 2019/9/27 16:37
 */
public enum NotificationStatusEunm {
    UNREAD(0),
    READ(1)
    ;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEunm(Integer status) {
        this.status = status;
    }
}
