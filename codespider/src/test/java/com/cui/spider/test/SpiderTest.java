package com.cui.spider.test;

import com.cui.code.spider.dal.dataobject.DoubanTopicDO;
import com.cui.code.spider.pageprocessor.*;
import com.cui.code.spider.pageprocessor.lynk.BookInfoPageProcessor;
import com.cui.code.spider.pipeline.*;
import com.cui.code.spider.pipeline.lynk.BookInfoPipeline;
import com.cui.code.spider.service.DoubanTopicService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        String groupCode = "trip";
        int start = 1647752321;
        Spider spider = Spider.create(new DoubanTopicListPageProcessor());
        spider.setDownloader(httpClientDownloader);
        spider.addUrl("https://www.douban.com/group/" + groupCode + "/discussion?start=" + start)
                .addPipeline(new DoubanTopicPipeline())
                .thread(3).run();
    }

    /**
     * 爬一下豆瓣的话题详情页
     */
    @Test
    public void testDoubanTopicDetail() {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        List<Proxy> proxies = new ArrayList<>();
        proxies.add(new Proxy("119.101.113.121", 9999));
        //proxies.add(new Proxy("119.101.114.46", 9999));
        //proxies.add(new Proxy("119.101.117.37", 9999));
        //proxies.add(new Proxy("119.101.116.6", 9999));
        //proxies.add(new Proxy("119.101.119.82", 9999));
        //proxies.add(new Proxy("119.101.115.142", 9999));
        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));

        String groupCode = "trip";
        int pageSize = 10;
        int lastId = 2288;
        DoubanTopicService doubanTopicService = new DoubanTopicService();

        while (true) {
            List<DoubanTopicDO> topicDOList = doubanTopicService.pageQuery(groupCode, lastId, pageSize);
            String[] urls = new String[pageSize];
            String urlPrefix = "https://www.douban.com/group/topic/";
            for (int i = 0; i < topicDOList.size(); i++) {
                urls[i] = urlPrefix + topicDOList.get(i).getTopicId();
            }

            try {
                Spider spider = Spider.create(new DoubanTopicDetailPageProcessor());
                spider.setDownloader(httpClientDownloader);
                spider.addUrl(urls)
                        .addPipeline(new DoubanTopicDetailPipeline())
                        .thread(2).run();
                if (topicDOList.size() < pageSize) {
                    break;
                }
                lastId = topicDOList.get(topicDOList.size() - 1).getId();
            } catch (Exception e) {
                System.out.println(lastId);
                e.printStackTrace();
            }
        }
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

    /**
     * 114挂号平台医院列表
     */
    @Test
    public void test114Hospital() {
        Spider spider = Spider.create(new HospitalListPageProcessor());

        int start = 1;
        String startURL = "http://www.114yygh.com/hp/" + start + "_0_0_0.htm";
        spider.addUrl(startURL)
                .addPipeline(new HospitalList114Pipeline())
                .thread(3).run();
    }

    /**
     * 京津冀旅游年卡预约详情信息
     */
    @Test
    public void testLynkBookInfoDetail() {
        int pageSize = 10;
        int startId = 1;
        int maxId = 100;
        String[] urls = new String[pageSize];

        while (true) {
            String urlPrefix = "http://zglynk.com/ITS/itsApp/goViewUserSubscribe.action?id=";
            for (int i = 0; i < pageSize; i++) {
                urls[i] = urlPrefix + startId;
                startId++;
            }

            try {
                Spider spider = Spider.create(new BookInfoPageProcessor());
                spider.addUrl(urls)
                        .addPipeline(new BookInfoPipeline())
                        .thread(1).run();
                if (startId >= maxId) {
                    break;
                }
            } catch (Exception e) {
                log.error("error id:{}", startId, e);
            }
        }
    }

}
