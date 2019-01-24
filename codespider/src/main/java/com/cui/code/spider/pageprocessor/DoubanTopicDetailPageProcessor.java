package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanTopicDetailDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 豆瓣小组话题详情爬虫
 *
 * @author cuishixiang
 * @date 2019-01-23
 */
public class DoubanTopicDetailPageProcessor implements PageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DoubanTopicDetailPageProcessor.class);

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private int groupCodeLength = "https://www.douban.com/group/".length();
    private int topicIdLength = "https://www.douban.com/group/topic/".length();
    private int peopleLength = "https://www.douban.com/people/".length();

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .addHeader("Host", "www.douban.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Referer", "https://www.douban.com/group/trip/discussion")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);


    @Override
    public void process(Page page) {
        String groupHref = page.getHtml().xpath("div[@class='aside']//div[@class='title']/a/@href").toString().trim();
        int lastIndexOf = groupHref.lastIndexOf("/");
        String groupCode = groupHref.substring(groupCodeLength, lastIndexOf);

        String topicId = page.getUrl().toString().substring(topicIdLength);
        String topicTitle = page.getHtml().xpath("div[@id='content']/h1/text()").toString().trim();
        String topicContent = page.getHtml().xpath("div[@class='topic-doc']//div[@class='topic-content']/html()").toString().trim();
        String authorHref = page.getHtml().xpath("div[@class='topic-content']/div/h3/span[1]/a/@href").toString().trim();
        String authorId = authorHref.substring(peopleLength, authorHref.length() - 1);

        String authorName = page.getHtml().xpath("div[@class='topic-content']/div/h3/span[1]/a/text()").toString().trim();
        String date = page.getHtml().xpath("div[@class='topic-content']/div/h3/span[2]/text()").toString().trim();

        // 未登录时无法看到点赞数和收藏数
        //String likeNum = page.getHtml().xpath("div[@class='action-react']/span[2]/text()").toString().trim();
        //String collectNum = page.getHtml().xpath("div[@class='action-collect']/span[2]/text()").toString().trim();

        LocalDateTime localDateTime = LocalDateTime.parse(date, timeFormatter);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);

        DoubanTopicDetailDO doubanTopicDetailDO = new DoubanTopicDetailDO();
        doubanTopicDetailDO.setGroupCode(groupCode);
        doubanTopicDetailDO.setTopicId(Integer.valueOf(topicId));
        doubanTopicDetailDO.setTopicName(topicTitle);
        doubanTopicDetailDO.setTopicContent(topicContent);
        doubanTopicDetailDO.setAuthorId(authorId);
        doubanTopicDetailDO.setAuthorName(authorName);

        doubanTopicDetailDO.setLikeCount(0);
        doubanTopicDetailDO.setCollectionCount(0);
        doubanTopicDetailDO.setPostedTime(Date.from(zonedDateTime.toInstant()));

        page.putField("doubanTopicDetailDO", doubanTopicDetailDO);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
