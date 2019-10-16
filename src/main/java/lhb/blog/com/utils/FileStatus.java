package lhb.blog.com.utils;

import lombok.Data;

/**
 * @author lhb
 * @date 2019/10/10 10:25
 */
@Data
public class FileStatus {
    private int code; //操作状态
    private String FileName; //文件名
    private String FileUrl; //文件上传路径

}
