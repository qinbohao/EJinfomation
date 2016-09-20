package main.java;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import sql.java.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author code4crafter@gmail.com <br>
 */
public class EjinfoProcessor implements PageProcessor {

int i=0;   
String URL_LIST="http://218.94.50.12/sunflower/candidate/candidateEnter.action?act=licensePrint&systemFlag=1&candidateId=";
    private Site site = Site
            .me()
           // .setDomain("http://10.1.30.98:8080")
            .setSleepTime(10)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            //.addHeader("Connection", "keep-alive")
            //.addHeader("Referer", "http://10.1.30.98:8080/competition/login.aspx")
            //.addHeader("Upgrade-Insecure-Requests", "1")
            //.addCookie("ASP.NET_SessionId","vmc0i355phzaiv45d4jzhu45");
            //.addCookie(".ASPXAUTH", "781006FB8063381FABCA2DD6E5E7A26A26370BA66E6526865BF0A5DCA154BD79F07770BAE665B7FEA3076F1D65535C2C0C2D02BAC311559A84C23DDB7B75C082F41BD38A452290C65AA107FCBB9F4F12");

    @Override
    public void process(Page page) {
        //列表页
    	//i++;
    	//page.addTargetRequest(URL_LIST+(8224933+i));
    	//page.addTargetRequest(URL_LIST+(8224933-i));
    	page.putField("name", page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[4]/td[2]/text()").toString());
    	page.putField("cardID", page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[5]/td[2]/text()").toString());
    	 if (page.getResultItems().get("name") == null) {
             //skip this page
             page.setSkip(true);
         }
    	 else{
    		 page.putField("schoolID", page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[13]/td/text()").toString());
    	    	page.putField("address", page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[11]/td[2]/text()").toString());
    	    	   
    	 }
    	
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //Spider.create(new EjinfoProcessor()).addUrl("http://218.94.50.12/sunflower/candidate/candidateEnter.action?act=licensePrint&systemFlag=1&candidateId=8224933")
         //       .run();
    	//mybatis的配置文件
        String resource = "conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = EjinfoProcessor.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource); 
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "sql.mybatis.Infomapper.getName";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        Info user = session.selectOne(statement, "秦博豪");
        System.out.println(user.getAddress());
    }
}