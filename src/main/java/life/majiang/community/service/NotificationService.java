package life.majiang.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Notification;
import life.majiang.community.model.NotificationExample;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    public void insert(Comment comment) {
//        private Integer id;
//        private Long gmtCreate;
//        private Integer status;//已读或未读
//        private Integer notifier;//通知发起人id
//        private String notifierName;//通知发起人昵称
//        private Integer receiver;//接收者id
//        private Integer outerId;//回复的那个页面的questionId
//        private String outerTitle;//回复的问题的title或者是评论内容本身（bilibili使用了一个缩略图）
//        private Integer type;//回复问题还是回复评论
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        User notifier = userMapper.selectByPrimaryKey(comment.getCommentator());
        notification.setNotifier(notifier.getId());
        notification.setNotifierName(notifier.getName());
        notification.setOuterid(comment.getQuestionId());

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            QuestionDTO questionDTO = questionService.getById(comment.getParentId());
            notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
            notification.setOuterTitle(questionDTO.getTitle());
            notification.setReceiver(questionDTO.getUser().getId());
        } else {
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            notification.setOuterTitle(parentComment.getContent());
            notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
            notification.setReceiver(parentComment.getCommentator());
        }
        notificationMapper.insertSelective(notification);
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExample(notificationExample);
        List<NotificationDTO> notificationDTOList = notificationList.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            if (notification.getType() == NotificationTypeEnum.REPLY_QUESTION.getType())
                notificationDTO.setTypeName(NotificationTypeEnum.REPLY_QUESTION.getName());
            else notificationDTO.setTypeName(NotificationTypeEnum.REPLY_COMMENT.getName());
            return notificationDTO;
        }).collect(Collectors.toList());
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(notificationDTOList);
        PageInfo pageInfo = new PageInfo(notificationList);
        paginationDTO.setPagination(pageInfo);

        return paginationDTO;
    }

    public Notification read(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //设置成已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        return notification;
    }

    public Long unreadCount(Integer id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long count = notificationMapper.countByExample(notificationExample);
        return count;
    }
}