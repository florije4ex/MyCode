package com.cui.code.spider.pageprocessor;

import com.cui.code.spider.dal.dataobject.HospitalDO;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 114挂号平台医院信息爬取
 *
 * @author cuiswing
 * @date 2019-06-28
 */
@Slf4j
public class HospitalListPageProcessor implements PageProcessor {

    private String commonPageURL = "http://www.114yygh.com/hp/{0}_0_0_0.htm";
    private String hospitalURL = "/hp/appoint/1/";
    private String timePrefix = "放号时间 : ";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Host", "www.114yygh.com")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .addHeader("Referer", "http://www.114yygh.com/hp/1_0_0_0.htm?")
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);

    @Override
    public void process(Page page) {
        String currentPageURL = page.getUrl().toString();
        log.info("fetch page url :{}", currentPageURL);

        String currentPageNumString = page.getHtml().xpath("input[@name='p_currentlyPage']/@value").toString();
        String totalPageNumString = page.getHtml().xpath("input[@name='p_totalPage']/@value").toString();

        Integer currentPageNum = Integer.parseInt(currentPageNumString);
        Integer totalPageNum = Integer.parseInt(totalPageNumString);
        if (currentPageNum < totalPageNum) {
            String addURL = MessageFormat.format(commonPageURL, currentPageNum + 1);
            page.addTargetRequests(Collections.singletonList(addURL));
        }

        List<Selectable> hospitals = page.getHtml().xpath("//*[@id='yiyuan_content']/div[@class='yiyuan_content_1']").nodes();
        List<HospitalDO> hospitalDOList = new ArrayList<>(hospitals.size());
        for (Selectable hospital : hospitals) {
            HospitalDO hospitalDO = new HospitalDO();

            String hospitalName = hospital.xpath("//p[@class='yiyuan_co_titl']/a/text()").toString().trim();
            String hospitalHref = hospital.xpath("//p[@class='yiyuan_co_titl']/a/@href").toString().trim();
            String hospitalLevel = hospital.xpath("//p[@class='yiyuan_co_titl']/span/text()").toString().trim();
            String hospitalId = hospitalHref.substring(hospitalURL.length(), hospitalHref.length() - 4);

            hospitalDO.setHospitalId(Integer.valueOf(hospitalId));
            hospitalDO.setName(hospitalName);
            hospitalDO.setLevel(hospitalLevel);


            List<Selectable> hospitalDetails = hospital.xpath("//div[@class='yiyuan_co_dd_div']/p").nodes();
            for (Selectable hospitalDetail : hospitalDetails) {
                String classAttribute = hospitalDetail.xpath("//b/@class").toString().trim();
                String value = hospitalDetail.xpath("allText()").toString().trim();
                switch (classAttribute) {
                    case "yiyuan_telico2":
                        hospitalDO.setStartTime(value.substring(timePrefix.length()));
                        break;
                    case "yiyuan_telico":
                        hospitalDO.setPhone(value);
                        break;
                    case "yiyuan_telico1":
                        hospitalDO.setAddress(value);
                        break;
                    default:
                        break;
                }
            }
            hospitalDOList.add(hospitalDO);
        }
        page.putField("hospitalDOList", hospitalDOList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
