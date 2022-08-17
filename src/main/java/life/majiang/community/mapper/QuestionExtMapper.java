package life.majiang.community.mapper;

public interface QuestionExtMapper {
    //阅读数自增的sql映射
    int incView(Integer id);
    //评论数自增的sql映射
    int incCommentCount(Integer id);
}