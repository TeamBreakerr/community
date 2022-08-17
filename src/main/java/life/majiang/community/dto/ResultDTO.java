package life.majiang.community.dto;

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

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
}