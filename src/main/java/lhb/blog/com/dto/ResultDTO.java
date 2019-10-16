package lhb.blog.com.dto;

import lhb.blog.com.exception.CustomizeErrorCode;
import lhb.blog.com.exception.CustomizeException;
import lombok.Data;

/**
 * @author lhb
 * @date 2019/9/27 9:27
 */
@Data
public class  ResultDTO <T>{
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        System.out.println(errorCode.getCode());
        System.out.println(errorCode.getMessage());
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        //System.out.println("进来了"+resultDTO.toString());
        return resultDTO;
    }


}
