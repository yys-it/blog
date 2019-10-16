package lhb.blog.com.enums;

/**
 * @author lhb
 * @date 2019/9/27 16:37
 */
public enum  CommtentTypeEnum {
    QUESTION(1),
    COMMENT(2),
    ;

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommtentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommtentTypeEnum commtentTypeEnum : CommtentTypeEnum.values()) {
            if (commtentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
