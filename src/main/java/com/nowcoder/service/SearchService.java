package com.nowcoder.service;

import com.nowcoder.model.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10412 on 2016/11/27.
 * 搜索模块
 */
@Service
public class SearchService {

    //参考官方文档 https://cwiki.apache.org/confluence/display/solr/Using+SolrJ


    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/wenda";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();

    private static final String QUESTION_TITLE_FIELD = "question_title";
    private static final String QUESTION_CONTENT_FIELD = "question_content";


    /**
     * 搜索问题函数（关键字，， 显示数量，）
     *
     * @param keywords 关键字
     * @param offset   偏移量
     * @param count    显示数量
     * @param hlPre    高亮起始
     * @param hlPos    高亮结束
     * @return 问题列表
     * @throws Exception
     */
    public List<Question> searchQuestion(String keywords, int offset, int count, String hlPre, String hlPos) throws Exception {
        List<Question> questionList = new ArrayList<>();

        //可参考官网资料：https://lucene.apache.org/solr/guide/6_6/highlighting.html

        SolrQuery query = new SolrQuery(keywords);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
//        query.setHighlightRequireFieldMatch(true);  //置为true，除非该字段的查询结果不为空才会被高亮。它的默认值是false，意味着它可能匹配某个字段却高亮一个不同的字段
//        query.set("hl.usePhraseHighlighter", true);   //如果一个查询中含有短语（引号框起来的）那么会保证一定要完全匹配短语的才会被高亮
//        query.set("hl.highlightMultiTerm", true);     //如果使用通配符和模糊搜索，那么会确保与通配符匹配的term会高亮. 默认为false，同时hl.usePhraseHighlighter要为true。
        query.set("df", QUESTION_TITLE_FIELD);
        query.set("hl.fl", QUESTION_TITLE_FIELD + "," + QUESTION_CONTENT_FIELD);

        QueryResponse response = client.query(query);

        //解析搜索获得到内容，并进行分类（标题还是内容）
        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Question q = new Question();
            q.setId(Integer.parseInt(entry.getKey()));
            if (entry.getValue().containsKey(QUESTION_CONTENT_FIELD)) {
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIELD);
                if (contentList.size() > 0) {
                    q.setContent(contentList.get(0));
                }
            }
            if (entry.getValue().containsKey(QUESTION_TITLE_FIELD)) {
                List<String> titleList = entry.getValue().get(QUESTION_TITLE_FIELD);
                if (titleList.size() > 0) {
                    q.setTitle(titleList.get(0));
                }
            }
            questionList.add(q);
        }
        return questionList;
    }

    /**
     * 问题索引
     *
     * @param qid
     * @param title
     * @param content
     * @return
     * @throws Exception
     */
    public boolean indexQuestion(int qid, String title, String content) throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", qid);
        doc.setField(QUESTION_TITLE_FIELD, title);
        doc.setField(QUESTION_CONTENT_FIELD, content);
        UpdateResponse response = client.add(doc, 1000);
        return response != null && response.getStatus() == 0;
    }
}
