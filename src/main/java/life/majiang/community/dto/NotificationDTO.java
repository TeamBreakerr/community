package life.majiang.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;//一个自增id
    private Long gmtCreate;//创建时间
    private Integer status;//已读1 未读0
    private Integer notifier;//创建者id
    private String notifierName;//创建者昵称
    private String outerTitle;//回复或评论的问题的标题
    private Integer outerid;//问题的id
    private String typeName;//“回复了问题” or “回复了评论”
    private Integer type;//1 or 0
}
