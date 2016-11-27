package com.nowcoder.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * Created by 10412 on 2016/8/8.
 */
@Service
public class JedisAdapter implements InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    public static void print(int index, Object obj)
    {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    //最后打包需要去除多余的main函数
    public static void mainx(String[] args)
    {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();


        //get  set

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "newhello");
        print(2, jedis.get("newhello"));
        jedis.setex("hello2", 15, "world");


        //incr  decr
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(3, jedis.get("pv"));
        jedis.incrBy("pv", 5);
        print(4, jedis.get("pv"));
        jedis.decr("pv");
        print(5, jedis.get("pv"));
        jedis.decrBy("pv", 5);
        print(6, jedis.get("pv"));

        print(7, jedis.keys("*"));


        //list

        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++)
        {
            jedis.lpush(listName, "a" + String.valueOf(i));

        }
        print(8, jedis.lrange(listName, 0, 12));
        print(9, jedis.lrange(listName, 0, 3));
        print(10, jedis.llen(listName));
        print(11, jedis.lpop(listName));
        print(12, jedis.llen(listName));
        print(13, jedis.lrange(listName, 0, 12));
        print(14, jedis.lrange(listName, 2, 6));
        print(15, jedis.lindex(listName, 3));
        print(16, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(17, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "bb"));
        print(18, jedis.lrange(listName, 0, 12));



        //hash

        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "15");
        jedis.hset(userKey, "phone", "12345678");
        print(19, jedis.hget(userKey, "age"));
        print(20, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(21, jedis.hgetAll(userKey));
        print(22, jedis.hexists(userKey, "email"));
        print(23, jedis.hexists(userKey, "name"));
        print(24, jedis.hkeys(userKey));
        print(25, jedis.hvals(userKey));

        jedis.hsetnx(userKey, "phone", "124578");
        jedis.hsetnx(userKey, "school", "ecjtu");
        print(26, jedis.hgetAll(userKey));



        //set

        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";

        for (int i = 0; i < 10; i++)
        {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i*i));
        }
        print(27, jedis.smembers(likeKey1));
        print(28, jedis.smembers(likeKey2));

        print(29, jedis.sdiff(likeKey1, likeKey2));     //集合的不同
        print(30, jedis.sinter(likeKey1, likeKey2));    //集合的交集
        print(31, jedis.sunion(likeKey1, likeKey2));    //集合的并集
        print(32, jedis.sismember(likeKey1, "12"));     //12是否是likeKey1的成员
        print(33, jedis.sismember(likeKey2, "16"));     //16是否是likeKey2的成员

        jedis.srem(likeKey1, "5");                      //将likeKey1中的5删除
        print(34, jedis.smembers(likeKey1));

        jedis.smove(likeKey2, likeKey1, "25");          //将likeKey2中的25移到likeKey1中
        print(35, jedis.smembers(likeKey1));

        print(36, jedis.scard(likeKey1));               //likeKey总的个数



        //sorted set

        String rankKey = "rankKey";
        jedis.zadd(rankKey, 50, "Tom");
        jedis.zadd(rankKey, 90, "Mike");
        jedis.zadd(rankKey, 60, "Jim");
        jedis.zadd(rankKey, 70, "Bob");
        jedis.zadd(rankKey, 80, "Lucy");

        print(37, jedis.zcard(rankKey));
        print(38, jedis.zcount(rankKey, 60, 100));
        print(39, jedis.zscore(rankKey, "Mike"));
        print(40, jedis.zrangeByScore(rankKey, 50, 90));    //根据分数排序
        jedis.zincrby(rankKey, 5, "Tom");                   //给Tom加5分
        print(41, jedis.zscore(rankKey, "Tom"));

        print(42, jedis.zrank(rankKey, "Jim"));
        print(43, jedis.zrevrank(rankKey, "Jim"));


        //连接池
/*

        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; i++)
        {
            Jedis j = pool.getResource();
            print(44, jedis.get("pv"));
            j.close();
        }
*/



        User user = new User();
        user.setHeadUrl("a.png");
        user.setName("xx");
        user.setPassword("ppp");
        user.setSalt("salt");
        user.setId(1);
        print(45, JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));


        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);
        print(46, user2);



    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }


    public long sadd(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e)
        {
            logger.error("发生异常"+e.getMessage());
        }
        finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }



    public long srem(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e)
        {
            logger.error("发生异常"+e.getMessage());
        }
        finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }



    public long scard(String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e)
        {
            logger.error("发生异常"+e.getMessage());
        }
        finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }



    public boolean sismenber(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e)
        {
            logger.error("发生异常"+e.getMessage());
        }
        finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return false;
    }


    public List<String> brpop(int timeout, String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        } finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }


    public long lpush(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        } finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }


    public List<String> lrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }



    public Jedis getJedis()
    {
        return pool.getResource();
    }





    public Transaction multi(Jedis jedis)
    {
        try {
            return jedis.multi();

        }catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        }

        return null;
    }


    public List<Object> exec(Transaction tx, Jedis jedis)
    {
        try {
            return tx.exec();

        }catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (tx != null)
            {
                try {
                    tx.close();
                }catch (Exception ioe)
                {
                    logger.error("发生异常" + ioe.getMessage());
                }
            }

            if (jedis != null)
            {
                jedis.close();
            }

        }

        return null;
    }



    public long zadd(String key, double score, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }



    public long zrem(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }


    public Set<String> zrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    public Set<String> zrevrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        } finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }


    public long zcard(String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        } finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }


    public Double zscore(String key, String member)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e)
        {
            logger.error("发生异常" + e.getMessage());
        } finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

}
