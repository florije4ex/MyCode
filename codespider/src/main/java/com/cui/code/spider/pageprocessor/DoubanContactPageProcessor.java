package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanContactDO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人页面数据解析
 *
 * @author CUI
 * @date 2020-01-12
 */
public class DoubanContactPageProcessor implements PageProcessor {
    private int peopleURLPrefixLength = "https://www.douban.com/people/".length();

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Host", "www.douban.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Referer", "https://www.douban.com/group/all")
            .addHeader("Cookie", "这里需要登陆后的cookie信息")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);

    @Override
    public void process(Page page) {
        Selectable fromUser = page.getHtml().xpath("div[@id='db-usr-profile']/div[@class='info']/ul/li[1]/a");
        String fromIdURL = fromUser.css("a", "href").toString();
        String fromName = fromUser.css("a", "text").toString();
        fromName = fromName.replace("的主页", "");
        String fromId = fromIdURL.substring(peopleURLPrefixLength, fromIdURL.length() - 1);

        List<Selectable> contactList = page.getHtml().xpath("dl[@class='obu']").nodes();
        List<DoubanContactDO> toList = new ArrayList<>();
        for (Selectable contact : contactList) {
            String toIdURL = contact.xpath("dd/a/@href").toString();
            String toId = toIdURL.substring(peopleURLPrefixLength, toIdURL.length() - 1);
            String toName = contact.xpath("dd/a/text()").toString();

            DoubanContactDO contactDO = new DoubanContactDO();
            contactDO.setToId(toId);
            contactDO.setToName(toName);
            toList.add(contactDO);
        }
        page.putField("fromId", fromId);
        page.putField("fromName", fromName);
        page.putField("toList", toList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
