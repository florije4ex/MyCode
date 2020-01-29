package com.cui.code.spider.pageprocessor.douban;

import com.cui.code.spider.dal.dataobject.douban.DoubanTopicReplyDO;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 豆瓣小组话题回复详情爬虫
 *
 * @author CUI
 * @since 2020-01-29
 */
@Slf4j
public class DoubanTopicReplyPageProcessor implements PageProcessor {

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private int groupCodeLength = "https://www.douban.com/group/".length();
    private int topicIdLength = "https://www.douban.com/group/topic/".length();
    private int peopleLength = "https://www.douban.com/people/".length();

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Host", "www.douban.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Referer", "https://www.douban.com/group/trip/discussion")
            .addHeader("Cookie", "ll=\"108288\"; bid=yGetHe6O9c4; __yadk_uid=LXkYEv2Sa7elyFIjbkqYrThuJrup2vgZ; douban-fav-remind=1; viewed=\"26609447\"; gr_user_id=ce6e9ce0-294d-4a90-9fdd-21a90338fe79; _vwo_uuid_v2=D3B807B60ED15FF32A301AF3F41EF66FD|d91cf8141cad0c60be74a82aefea3383; __gads=ID=0cab5b3a9dd24626:T=1577896611:S=ALNI_MYfulOC5wkKiiYe0ORL5qh4lnUrwA; push_doumail_num=0; douban-profile-remind=1; ct=y; __utmc=30149280; __utmz=30149280.1580043514.27.8.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1580307279%2C%22https%3A%2F%2Fmovie.douban.com%2Fsubject%2F30390700%2F%22%5D; _pk_ses.100001.8cb4=*; ap_v=0,6.0; __utma=30149280.465911115.1568805761.1580043514.1580307279.28; frodotk_db=\"f5a76bb32e2ece5be0f643198a8c6b11\"; dbcl2=\"93556208:phASes9uu5k\"; ck=GEoI; _pk_id.100001.8cb4=9410ef3992f02e19.1568805761.29.1580309125.1580044381.; __utmv=30149280.9355; __utmb=30149280.80.9.1580309125362; push_noty_num=19")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);


    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().xpath("div[@class='paginator']/span[@class='next']/a").links().all());

        String groupHref = page.getHtml().xpath("div[@class='aside']//div[@class='title']/a/@href").toString().trim();
        int lastIndexOf = groupHref.lastIndexOf("/");
        String groupCode = groupHref.substring(groupCodeLength, lastIndexOf);

        lastIndexOf = page.getUrl().toString().lastIndexOf("/");
        String topicIdString = page.getUrl().toString().substring(topicIdLength, lastIndexOf);
        Integer topicId = Integer.valueOf(topicIdString);


        // 未登录时无法看到点赞数和收藏数
        //String likeNum = page.getHtml().xpath("div[@class='action-react']/span[2]/text()").toString().trim();

        List<DoubanTopicReplyDO> doubanTopicReplyDOList = new ArrayList<>();
        List<Selectable> commentList = page.getHtml().xpath("ul[@id='comments']/li").nodes();
        for (Selectable comment : commentList) {
            String replyIdHref = comment.xpath("div[@class='bg-img-green']/h4/a/@href").toString();
            String replyId = replyIdHref.substring(peopleLength, replyIdHref.length() - 1);
            String replyName = comment.xpath("div[@class='bg-img-green']/h4/a/text()").toString().trim();
            String replyTime = comment.xpath("div[@class='bg-img-green']/h4/span/text()").toString().trim();

            String replyContent = comment.xpath("p[@class='reply-content']/text()").toString().trim();

            LocalDateTime localDateTime = LocalDateTime.parse(replyTime, timeFormatter);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);

            DoubanTopicReplyDO doubanTopicReplyDO = new DoubanTopicReplyDO();
            doubanTopicReplyDO.setGroupCode(groupCode);
            doubanTopicReplyDO.setTopicId(topicId);
            doubanTopicReplyDO.setReplyId(replyId);
            doubanTopicReplyDO.setReplyName(replyName);
            doubanTopicReplyDO.setReplyTime(Date.from(zonedDateTime.toInstant()));
            doubanTopicReplyDO.setReplyContent(replyContent);
            doubanTopicReplyDO.setLikeCount(0);
            doubanTopicReplyDO.setDeleted(0);

            doubanTopicReplyDOList.add(doubanTopicReplyDO);
        }

        page.putField("groupCode", groupCode);
        page.putField("topicId", topicId);
        page.putField("doubanTopicReplyDOList", doubanTopicReplyDOList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
