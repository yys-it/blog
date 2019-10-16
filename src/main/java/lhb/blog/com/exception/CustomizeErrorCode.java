package lhb.blog.com.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "你找的问题已经不在了，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或者评论进行回复"),
    NOT_LOGIN(2003,"用户未登录，请先登录在进行评论"),
    SYSTEM_ERROR(2004,"系统好像出了点异常，要不你再试试？"),
    TYPE_PARAM_NOT_FOUND(2005,"评论类型错误或者不存在"),
    COMMENT_PARAM_NOT_FOUND(2006, "所回复的评论不存在，要不换个试试？"),
    QUESTION_PARAM_NOT_FOUND(2007,"所回复的问题不存在，要不换个试试？"),
    CONTENT_NOT_FOUND(2008,"评论不能为空"),
    NOTIFICATION_USER_NOT_EQUL(2009, "貌似你不是这条评论的主人？"),
    QUESTION_USER_NOT_EQUL(2010, "貌似你不是这条问题的主人？"),
    NOTIFICATION_NOT_FOUND(2011,"找不到哎，这条回复怕不是不见了？")

    ;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


}
