package com.nowcoder.controller;

import com.nowcoder.model.EntityType;
import com.nowcoder.model.Question;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.FollowService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.SearchService;
import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10412 on 2016/11/27.
 * 搜索模块（solr）
 */
@Controller
public class SearchController
{
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    /**
     * 搜索
     * @param model
     * @param keyword 搜索关键词
     * @param offset
     * @param count 数量
     * @return 搜索结果页面
     */
    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try
        {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count, "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList)
            {
                Question q = questionService.getById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent() != null)
                {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null)
                {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                //后期加上评论问题的评论数量
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索评论失败" + e.getMessage());
        }
        return "result";
    }
}
