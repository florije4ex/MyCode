package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanTopicDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 豆瓣小组话题列表爬虫
 *
 * @author cuishixiang
 * @date 2018-12-25
 */
public class DoubanTopicListPageProcessor implements PageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DoubanTopicListPageProcessor.class);

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String yearPrefix = LocalDate.now().getYear() + "-";
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private int groupCodeLength = "https://www.douban.com/group/".length();
    private int topicLength = "https://www.douban.com/group/topic/".length();
    private int peopleLength = "https://www.douban.com/people/".length();


    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Host", "www.douban.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Referer", "https://www.douban.com/group/all")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);


    @Override
    public void process(Page page) {
        log.info("fetch page url :{}", page.getUrl().toString());
        page.addTargetRequests(page.getHtml().xpath("div[@class='paginator']/span[@class='next']/a").links().all());

        int discussionIndex = page.getUrl().toString().indexOf("discussion");
        String groupCode = page.getUrl().toString().substring(groupCodeLength, discussionIndex - 1);

        List<Selectable> topics = page.getHtml().xpath("table[@class='olt']/tbody/tr").nodes();
        topics.remove(0);
        List<DoubanTopicDO> doubanTopicDOList = new ArrayList<>(topics.size());
        for (Selectable topic : topics) {
            DoubanTopicDO doubanTopicDO = new DoubanTopicDO();

            String topicHref = topic.xpath("//td[1]/a/@href").toString();
            String topicId = topicHref.substring(topicLength, topicHref.length() - 1);
            String title = topic.xpath("//td[1]/a/@title").toString();

            String authorHref = topic.xpath("//td[2]/a/@href").toString();
            String authorId = authorHref.substring(peopleLength, authorHref.length() - 1);
            String authorName = topic.xpath("//td[2]/a/text()").toString().trim();

            String replayCount = topic.xpath("//td[3]/text()").toString().trim();
            if (replayCount.isEmpty()) {
                doubanTopicDO.setReplyCount(0);
            } else {
                doubanTopicDO.setReplyCount(Integer.valueOf(replayCount));
            }

            String date = topic.xpath("//td[4]/text()").toString().trim();
            ZonedDateTime zonedDateTime;
            if (date.startsWith("20")) {
                LocalDate localDate = LocalDate.parse(date, dateFormatter);
                zonedDateTime = ZonedDateTime.of(localDate, LocalTime.MIN, zoneId);
            } else {
                LocalDateTime localDateTime = LocalDateTime.parse(yearPrefix + date, timeFormatter);
                zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
            }

            doubanTopicDO.setGroupCode(groupCode);
            doubanTopicDO.setTopicId(Integer.valueOf(topicId));
            doubanTopicDO.setTopicName(title.length() > 100 ? title.substring(0, 97) + "…" : title);
            doubanTopicDO.setAuthorId(authorId);
            doubanTopicDO.setAuthorName(authorName);
            doubanTopicDO.setLastReplyTime(Date.from(zonedDateTime.toInstant()));
            doubanTopicDOList.add(doubanTopicDO);
        }
        page.putField("doubanTopicDOList", doubanTopicDOList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
