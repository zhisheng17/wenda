package com.nowcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10412 on 2016/8/3.
 * 敏感词模块
 */
@Service
public class SensitiveService implements InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);


    /**
     * 读取敏感词文件
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);

            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                addWord(lineTxt.trim());
            }

            read.close();

        }catch (Exception e)
        {
            logger.error("读取敏感词文件失败！"+e.getMessage());
        }
    }



    //增加关键字abc
    private void addWord(String lineTxt)
    {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length() ; i++)
        {
            Character c = lineTxt.charAt(i);


            if (isSymbol(c))
            {
                continue;
            }



            TrieNode node = tempNode.getSubNode(c);

            if (node == null)
            {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }


            tempNode = node;

            if(i == lineTxt.length() - 1)
            {
                tempNode.setkeywordEnd(true);
            }

        }
    }

    private class TrieNode
    {
        //是不是关键词的结尾
        private boolean end = false;

        //当前节点下面的子节点ab ac ad
        private Map<Character, TrieNode> subNodes = new HashMap<Character, TrieNode>();

        public void addSubNode(Character key, TrieNode node)
        {
            subNodes.put(key, node);
        }


        TrieNode getSubNode(Character key)
        {
            return subNodes.get(key);
        }

        boolean isKeywordEnd()
        {
            return end;
        }

        void setkeywordEnd(boolean end)
        {
            this.end = end;
        }
    }


    private TrieNode rootNode = new TrieNode();

    //加强过滤信息
    private boolean isSymbol(char c)
    {
        int ic = (int) c;
        //东亚文字0x2E80-0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    //过滤
    public String filter(String text)
    {
        if (StringUtils.isBlank(text))
        {
            return text;
        }


        StringBuilder result = new StringBuilder();

        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;


        while (position < text.length())
        {
            char c = text.charAt(position);

            if (isSymbol(c))
            {
                if (tempNode == rootNode)
                {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            if (tempNode == null)
            {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }
            else if (tempNode.isKeywordEnd())
            {
                //发现敏感词
                result.append(replacement);     //打码
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            }
            else
            {
                ++position;
            }
        }

        result.append(text.substring(begin));
        return result.toString();

    }


    //测试
    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        System.out.println(s.filter("你好色 情！赌 博！"));
    }
}
