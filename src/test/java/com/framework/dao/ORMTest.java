package com.framework.dao;

import com.framework.orm.core.Query;
import com.framework.orm.core.QueryFactory;
import com.framework.orm.core.TableContext;
import com.framework.orm.core.impl.MySQLQueryImpl;
import com.fuyouj.entity.test.Paper_url;
import com.fuyouj.entity.test.dto.PaperDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/6 20:38
 */
public class ORMTest {
    @Test
    void testDelete(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(222);
        new MySQLQueryImpl().deleteByPrimaryKey(paperUrl.getClass(),222);
    }
    @Test
    void testAdd(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(223);
        paperUrl.setURL("safsff?????");
        new MySQLQueryImpl().insert(paperUrl);
    }
    @Test
    void testUpdate(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(222);
        paperUrl.setURL("aaa");
        new MySQLQueryImpl().updateByPrimaryKey(paperUrl);
    }
    @Test
    void testQueryRows(){
        TableContext.loadPoTables();
        List<Object> list = new MySQLQueryImpl().queryRows("select * from paper_url",
                Paper_url.class, null);
//        list.forEach(a-> System.out.println(a));
    }
    @Test
    void 连表查询测试 (){
        String  sql = "SELECT paper_url.*,paper.examId FROM\n" +
                "paper_url,paper\n";
        List<Object> list = new  MySQLQueryImpl().queryRows(sql, PaperDTO.class,null);
        list.forEach(a-> System.out.println(a));
    }
    @Test
    void 查询某个值(){
        String sql = "SELECT count(1) from\n" +
                "paper_url";
        Object value = new MySQLQueryImpl().queryValue(sql, null);
        System.out.println(value);
        System.out.println(new MySQLQueryImpl().queryNumber(sql, null));
        Query query = QueryFactory.createQuery();
        System.out.println(query.queryValue(sql, null));
    }
    @Test
    void 测试连接池(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000 ; i++) {
            testQueryRows();
        }
        long end = System.currentTimeMillis();
        System.out.println("不使用连接池的时间:"+(System.currentTimeMillis()-start)/1000);
    }
}