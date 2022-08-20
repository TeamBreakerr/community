package life.majiang.community.mapper;

import life.majiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    //阅读数自增的sql映射
    int incView(Integer id);
    //评论数自增的sql映射
    int incCommentCount(Integer id);
    //查询tag标签相关的问题
    List<Question> selectRelated(Question question);
}