package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.model.*;
import com.nowcoder.service.*;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10412 on 2016/8/1.
 * 问题模块
 */
@Controller
public class QuestionController
{

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;


    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;


    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    EventProducer eventProducer;


    /**
     * 提问题
     * @param title  问题title
     * @param content  问题描述内容
     * @return
     */
    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content)
    {
        try {

            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if (hostHolder.getUser() == null)
            {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
//                return WendaUtil.getJSONString(999);

            }
            else
            {
                question.setUserId(hostHolder.getUser().getId());
            }

            if (questionService.addQuestion(question) > 0)
            {
                eventProducer.fireEvent(new EventModel(EventType.ADD_QUESTION).setActorId(question.getUserId()).setEntityId(question.getId()).setExt("title", question.getTitle()).setExt("content", question.getContent()));
                return WendaUtil.getJSONString(0);
            }

        }catch (Exception e)
        {
            logger.error("增加题目失败"+e.getMessage());
        }

        return WendaUtil.getJSONString(1, "失败");

    }


    /**
     * 问题详情
     * @param model
     * @param qid
     * @return 返回问题详情页面
     */
    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid)
    {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
       // model.addAttribute("user", userService.getUser(question.getUserId()));


        //评论
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<ViewObject>();

        for (Comment comment : commentList)
        {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);

            if (hostHolder.getUser() == null)
            {
                vo.set("liked", 0);
            }
            else
            {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }

            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.getUser(comment.getUserId()));
            comments.add(vo);

        }

        model.addAttribute("comments", comments);

        List<ViewObject> followUsers = new ArrayList<ViewObject>();
        // 获取关注的用户信息
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);
        for (Integer userId : users)
        {
            ViewObject vo = new ViewObject();
            User u = userService.getUser(userId);
            if (u == null)
            {
                continue;
            }
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }

        model.addAttribute("followUsers", followUsers);
        if (hostHolder.getUser() != null)
        {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
        }
        else
        {
            model.addAttribute("followed", false);
        }

        return "detail";
    }


}
