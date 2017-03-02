package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by 10412 on 2016/8/4.
 * 评论模块
 */
@Controller
public class CommentController
{
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;


    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;


    /**
     * 增加评论
     * @param questionId 问题id
     * @param content   评论内容
     * @return 问题详情
     */
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId, @RequestParam("content") String content)
    {
        try {
            Comment comment = new Comment();
            comment.setContent(content);

            //判断用户是否登录
            if (hostHolder.getUser() != null)
            {
                comment.setUserId(hostHolder.getUser().getId());
            }
            else
            {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
                //return "redirect:/reglogin";
            }

            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            commentService.addComment(comment);

            //评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);


            //如果评论了问题，发出一个评论事件
          eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));

        }catch (Exception e)
        {
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + questionId;
    }
}
