package life.majiang.community.dto;

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(CustomizeException e) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(e.getCode());
        resultDTO.setMessage(e.getMessage());
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeErrorCode code) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code.getCode());
        resultDTO.setMessage(code.getMessage());
        return resultDTO;
    }

    public static <T>ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(CustomizeErrorCode.SUCCESS.getMessage());
        resultDTO.setCode(CustomizeErrorCode.SUCCESS.getCode());
        resultDTO.setData(t);
        return resultDTO;
    }
}