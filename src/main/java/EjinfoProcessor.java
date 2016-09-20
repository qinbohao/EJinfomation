package main.java;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import sql.java.*;

/**
 * @author code4crafter@gmail.com <br>
 */
public class EjinfoProcessor implements PageProcessor {

int i=0;   

String URL_LIST="http://218.94.50.12/sunflower/candidate/candidateEnter.action?act=licensePrint&systemFlag=1&candidateId=";
    private Site site = Site
            .me()
            .setSleepTime(10)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
    @Override
    public void process(Page page) {
        //列表页
    	i++;
    	page.addTargetRequest(URL_LIST+(8224933+i));
    	page.addTargetRequest(URL_LIST+(8224933-i));
    	Info info=new Info();
    	info.setName(page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[4]/td[2]/text()").toString());
    	info.setCardid(page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[5]/td[2]/text()").toString());
    	info.setSchoolid(page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[13]/td/text()").toString());
	    info.setAddress(page.getHtml().xpath("/html/body/center/table/tbody/tr[1]/td/table/tbody/tr[11]/td[2]/text()").toString());  	   
    	 if (page.getResultItems().get("name") == null) {
             //skip this page
             page.setSkip(true);
         }
    	 else{
    		page.putField("info", info);
    		
    	 }
    	
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new EjinfoProcessor()).addUrl("http://218.94.50.12/sunflower/candidate/candidateEnter.action?act=licensePrint&systemFlag=1&candidateId=8224933")
               .run();
    	//mybatis的配置文件
       
    }
}