package com.cui.spider.test;

import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 爬虫入门测试
 * <p>
 * Created by cuishixiang on 2017-10-24.
 */
public class SpiderTest {

    /**
     * 原始方法解析出字符串
     */
    @Test
    public void testBaidu() {

        String baiduUrl = "http://www.baidu.com";
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(baiduUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    /**
     * webMagic框架解析
     */
    @Test
    public void testWebMagic() {
        Spider.create(new GithubRepoPageProcessor())
                //从https://github.com/code4craft开始抓
                .addUrl("https://github.com/code4craft")
                //设置Scheduler，使用Redis来管理URL队列
                .setScheduler(new RedisScheduler("localhost"))
                .addPipeline(new JsonFilePipeline("testFile"))
                .thread(5)
                .run();
    }
}
