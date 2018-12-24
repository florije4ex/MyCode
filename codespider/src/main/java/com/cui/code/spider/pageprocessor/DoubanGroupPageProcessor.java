package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 豆瓣小组爬虫
 *
 * @author cuishixiang
 * @date 2018-12-23
 */
public class DoubanGroupPageProcessor implements PageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DoubanGroupPageProcessor.class);

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("创建于yyyy-MM-dd");
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private int leng = "https://www.douban.com/group/".length();

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

        List<Selectable> groups = page.getHtml().css("div.clist2").nodes();
        List<DoubanGroupDO> doubanGroupDOList = new ArrayList<>(groups.size());
        for (Selectable group : groups) {
            String logoUrl = group.css("img", "src").get();
            if (logoUrl != null) {
                DoubanGroupDO doubanGroupDO = new DoubanGroupDO();

                String name = group.xpath("span[@class='pl2']/a/text()").toString();
                String url = group.xpath("span[@class='pl2']/a/@href").toString();
                if (url.endsWith("/")) {
                    doubanGroupDO.setCode(url.substring(leng, url.length() - 1));
                } else {
                    doubanGroupDO.setCode(url.substring(leng));
                }

                String date = group.xpath("div[@style='float:right;']/text()").toString().trim();
                String people = group.xpath("div[@style='float:right;']/a/text()").toString().trim();

                LocalDate localDate = LocalDate.parse(date, dateFormatter);
                ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, LocalTime.MIN, zoneId);

                doubanGroupDO.setName(name);
                doubanGroupDO.setLogoUrl(logoUrl);
                doubanGroupDO.setAttentionUser(Integer.valueOf(people.substring(0, people.length() - 1)));
                doubanGroupDO.setGroupCreateDate(Date.from(zonedDateTime.toInstant()));
                doubanGroupDOList.add(doubanGroupDO);
            }
        }
        page.putField("doubanGroupDOList", doubanGroupDOList);

    }

    @Override
    public Site getSite() {
        return site;
    }
}
