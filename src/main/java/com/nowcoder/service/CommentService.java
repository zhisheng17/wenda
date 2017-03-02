package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 10412 on 2016/8/4.
 * 评论模块
 */
@Service
public class CommentService
{
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * 根据 entityId 和 entityType 查询评论并按时间降序排序
     * @param entityId  实体id
     * @param entityType  实体类型
     * @return 评论列表
     */
    public List<Comment> getCommentsByEntity(int entityId, int entityType)
    {
        return commentDAO.selectCommentByEntity(entityId, entityType);
    }

    /**
     * 增加评论
     * htmlEscape HTML特殊字符替换
     * sensitiveService.filter 敏感词过滤
     * @param comment 评论
     * @return comment id
     */
    public int addComment(Comment comment)
    {
        //增加评论时也要过滤敏感词
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }

    /**
     * 根据 entityId 和 entityType 查询评论，并获取评论数量
     * @param entityId
     * @param entityType
     * @return 评论数量
     */
    public int getCommentCount(int entityId, int entityType)
    {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    /**
     * 根据 userId 查询用户评论的数量
     * @param userId 用户id
     * @return 用户评论的数量
     */
    public int getUserCommentCount(int userId) {
        return commentDAO.getUserCommentCount(userId);
    }

    /**
     * 根据id更新评论的状态（1代表删除，0代表存在），并不是真正删除评论
     * @param commentId
     * @return 评论的状态
     */
    public boolean deleteComment(int commentId)
    {
        return commentDAO.updateStatus(commentId, 1) > 0;
    }

    /**
     * 根据id查询评论
     * @param id
     * @return 评论信息
     */
    public Comment getCommentById(int id)
    {
        return commentDAO.getCommentById(id);
    }



}
