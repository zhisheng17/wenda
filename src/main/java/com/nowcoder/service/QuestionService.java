package com.nowcoder.service;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 10412 on 2016/7/15.
 * 问题模块
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * 据 id 查询所提的问题
     * @param id
     * @return 问题
     */
    public Question getById(int id)
    {
        return questionDAO.getById(id);
    }

    /**
     * 添加提问（问题的题目和内容要进行过滤）
     * htmlEscape HTML特殊字符替换
     * sensitiveService.filter 敏感词过滤
     * @param question
     * @return
     */
    public int addQuestion(Question question)
    {
        //过滤html标签属性的特殊字符
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));


        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));


        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }


    /**
     * 获取最近的问题列表
     * @param userId
     * @param offset
     * @param limit
     * @return 问题列表
     */
    public List<Question> getLatestQuestions(int userId, int offset, int limit)
    {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    /**
     * 更新评论的数量
     * @param id
     * @param count
     * @return
     */
    public int updateCommentCount(int id, int count)
    {
        return questionDAO.updateCommentCount(id, count);
    }
}
