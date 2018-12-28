package com.cui.spider.test;

import com.cui.code.spider.pageprocessor.DoubanGroupMembersPageProcessor;
import com.cui.code.spider.pageprocessor.DoubanGroupPageProcessor;
import com.cui.code.spider.pageprocessor.DoubanTopicListPageProcessor;
import com.cui.code.spider.pipeline.DoubanGroupMembersPipeline;
import com.cui.code.spider.pipeline.DoubanGroupPipeline;
import com.cui.code.spider.pipeline.DoubanTopicPipeline;
import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 爬一下豆瓣的小组列表页
     */
    @Test
    public void testDoubanGroup() {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        List<Proxy> proxies = new ArrayList<>();
        proxies.add(new Proxy("223.99.214.21", 53281));
        proxies.add(new Proxy("118.187.58.34", 53281));
        proxies.add(new Proxy("183.237.206.92", 53281));

        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));
        Spider spider = Spider.create(new DoubanGroupPageProcessor());
        spider.setDownloader(httpClientDownloader);

        spider.addUrl("https://www.douban.com/group/all?start=11925")
                .addPipeline(new DoubanGroupPipeline())
                .thread(3).run();
    }

    /**
     * 爬一下豆瓣的话题列表页
     */
    @Test
    public void testDoubanTopic() {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        List<Proxy> proxies = new ArrayList<>();
        proxies.add(new Proxy("219.234.5.128", 3128));
        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));

        String groupCode = "635609";
        Spider spider = Spider.create(new DoubanTopicListPageProcessor());
        spider.setDownloader(httpClientDownloader);
        spider.addUrl("https://www.douban.com/group/" + groupCode + "/discussion?start=0")
                .addPipeline(new DoubanTopicPipeline())
                .thread(3).run();
    }

    /**
     * 豆瓣小组的成员信息
     */
    @Test
    public void testDoubanGroupMembers() {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        List<Proxy> proxies = new ArrayList<>();
        proxies.add(new Proxy("61.128.208.94", 3128));
        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));

        Spider spider = Spider.create(new DoubanGroupMembersPageProcessor());
        spider.setDownloader(httpClientDownloader);

        String groupId = "newsreel";
        int start = 35;
        String membersURL = "https://www.douban.com/group/" + groupId + "/members?start=" + start;
        spider.addUrl(membersURL)
                .addPipeline(new DoubanGroupMembersPipeline())
                .thread(3).run();
    }
}
