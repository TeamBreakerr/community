package life.majiang.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String content;
    private Integer parentId;
    private Integer type;
}
