package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.DoubanGroupMemberDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cuishixiang
 * @date 2018-12-27
 */
public class DoubanGroupMembersPageProcessor implements PageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DoubanGroupMembersPageProcessor.class);

    private int groupURLPrefixLength = "https://www.douban.com/group/".length();
    private int peopleURLPrefixLength = "https://www.douban.com/people/".length();

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

        int membersIndex = page.getUrl().toString().indexOf("members");
        String groupCode = page.getUrl().toString().substring(groupURLPrefixLength, membersIndex - 1);

        List<Selectable> mods = page.getHtml().css("div.article>div.mod").nodes();
        List<DoubanGroupMemberDO> memberDOList = new ArrayList<>(35);
        if (mods.size() > 3) {
            Selectable leader = mods.get(0);

            String ownerHref = leader.xpath("div[@class='name']/a/@href").toString();
            String ownerId = ownerHref.substring(peopleURLPrefixLength, ownerHref.length() - 1);
            String ownerName = leader.xpath("div[@class='name']/a/text()").toString().trim();

            page.putField("groupCode", groupCode);
            page.putField("ownerId", ownerId);
            page.putField("ownerName", ownerName);

            Selectable adminNode = mods.get(1);
            List<Selectable> admins = adminNode.xpath("div[@class='member-list']/ul/li").nodes();
            for (Selectable admin : admins) {
                DoubanGroupMemberDO doubanGroupMemberDO = getDoubanGroupMemberDO(groupCode, admin, 1);
                memberDOList.add(doubanGroupMemberDO);
            }

            Selectable memberNode = mods.get(2);
            List<Selectable> members = memberNode.xpath("div[@class='member-list']/ul/li").nodes();
            for (Selectable member : members) {
                DoubanGroupMemberDO doubanGroupMemberDO = getDoubanGroupMemberDO(groupCode, member, 0);
                memberDOList.add(doubanGroupMemberDO);
            }
        } else {
            List<Selectable> members = mods.get(0).xpath("div[@class='member-list']/ul/li").nodes();
            for (Selectable member : members) {
                DoubanGroupMemberDO doubanGroupMemberDO = getDoubanGroupMemberDO(groupCode, member, 0);
                memberDOList.add(doubanGroupMemberDO);
            }
        }
        page.putField("doubanGroupMemberDOList", memberDOList);

    }

    private DoubanGroupMemberDO getDoubanGroupMemberDO(String groupCode, Selectable liNode, int type) {
        String adminHref = liNode.xpath("div[@class='name']/a/@href").toString();
        String adminId = adminHref.substring(peopleURLPrefixLength, adminHref.length() - 1);
        String adminName = liNode.xpath("div[@class='name']/a/text()").toString().trim();
        String cityName = liNode.xpath("div[@class='name']/span/text()").toString().trim();
        String location = "";
        if (!cityName.isEmpty()) {
            location = cityName.substring(1, cityName.length() - 1);
        }

        DoubanGroupMemberDO doubanGroupMemberDO = new DoubanGroupMemberDO();
        doubanGroupMemberDO.setGroupCode(groupCode);
        doubanGroupMemberDO.setType(type);
        doubanGroupMemberDO.setMemberId(adminId);
        doubanGroupMemberDO.setMemberName(adminName);
        doubanGroupMemberDO.setLocation(location);
        return doubanGroupMemberDO;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
