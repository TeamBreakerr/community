package life.majiang.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.*;
import life.majiang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationService  notificationService;

    @Transactional
    public void insert(Comment comment) {
        //异常处理
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        //评论(一级评论) 和 回复(二级评论)
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //评论
            //检查评论的问题是否存在
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //评论数加一
            questionExtMapper.incCommentCount(question.getId());
            notificationService.insert(comment);
        } else {
            //回复
            //检查回复的评论是否存在
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //回复数加一
            commentExtMapper.incCommentCount(comment.getParentId());
            notificationService.insert(comment);
        }
    }

    //通过问题id找到问题下的回复，并包装成CommentDTO
    public List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum commentTypeEnum) {
        //找到了所有的回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.size() == 0)
            return new ArrayList<>();
        //要把每条回复和创建者的信息绑定，展示该回复者的头像和昵称

        //取出所有评论的用户id
        Set<Integer> userIdSet = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> UserIdList = new ArrayList();
        UserIdList.addAll(userIdSet);
        //从数据库中找出所有的用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(UserIdList);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
}
