package life.majiang.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String content;
    private Integer parentId;
    private Integer type;
    private Integer questionId;
}
