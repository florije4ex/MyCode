package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.nio.charset.StandardCharsets;

/**
 * 更新小组组长
 *
 * @author cuishixiang
 * @date 2018-12-26
 */
public class DoubanGroupUpdatePageProcessor implements PageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DoubanGroupPageProcessor.class);

    private int groupCodeLength = "https://www.douban.com/group/".length();
    private int peopleLength = "https://www.douban.com/people/".length();


    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Host", "www.douban.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Referer", "https://www.douban.com/group/all")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(30000);


    @Override
    public void process(Page page) {
        String groupCode = page.getUrl().toString().substring(groupCodeLength);
        page.putField("code", groupCode);

        String name = page.getHtml().xpath("div[@class='group-board']/p/a/text()").toString();
        if (name == null || name.isEmpty()) {
            return;
        }
        String peopleURL = page.getHtml().xpath("div[@class='group-board']/p/a/@href").toString();
        String ownerId = peopleURL.substring(peopleLength, peopleURL.length() - 1);

        DoubanGroupDO doubanGroupDO = new DoubanGroupDO();
        doubanGroupDO.setCode(groupCode);
        doubanGroupDO.setOwnerId(ownerId);
        doubanGroupDO.setOwnerName(name.trim());
        page.putField("doubanGroupDO", doubanGroupDO);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
