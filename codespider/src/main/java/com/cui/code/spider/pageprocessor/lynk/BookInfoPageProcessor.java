package com.cui.code.spider.pageprocessor.lynk;

import com.cui.code.spider.dal.dataobject.lynk.BookInfoDO;
import lombok.extern.slf4j.Slf4j;
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
 * 预约信息爬取
 *
 * @author CUI
 * @date 2019-12-27
 */
@Slf4j
public class BookInfoPageProcessor implements PageProcessor {
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private static final String JSESSIONID = "XXXXXXXXXXXXXX";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Cookie", "JSESSIONID=" + JSESSIONID)
            .setCharset(StandardCharsets.UTF_8.name())
            .setTimeOut(20000);

    @Override
    public void process(Page page) {
        String currentPageURL = page.getUrl().toString();
        log.info("fetch page url :{}", currentPageURL);
        int lastIndexOf = currentPageURL.lastIndexOf("=");
        String bookIdString = currentPageURL.substring(lastIndexOf + 1);

        List<Selectable> cardTableRows = page.getHtml().css("table.ticket-info").nodes().get(0).xpath("tr").nodes();

        String bookName = page.getHtml().xpath("p[@class='mart30']").nodes().get(0).xpath("p/text()").toString().trim();
        String bookDate = page.getHtml().xpath("p[@class='mart30']").nodes().get(3).xpath("p/text()").toString().trim().replace("预约时间：", "");
        String bookStatus = page.getHtml().xpath("p[@class='mart30']").nodes().get(4).xpath("p/html()").toString()
                .trim().replace("预约状态：", "").trim()
                .replace("<!--", "").replace("-->", "");

        List<BookInfoDO> bookInfoDOList = new ArrayList<>();
        for (Selectable book : cardTableRows) {
            BookInfoDO bookInfoDO = new BookInfoDO();
            bookInfoDO.setBookId(Integer.parseInt(bookIdString));
            bookInfoDO.setBookName(bookName);
            LocalDate localDate = LocalDate.parse(bookDate, dateFormatter);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, LocalTime.MIN, zoneId);
            bookInfoDO.setBookDate(Date.from(zonedDateTime.toInstant()));
            bookInfoDO.setBookStatus(bookStatus);

            String name = book.xpath("//span/text()").toString().trim();
            String cardNo = book.xpath("//td[2]/text()").toString().trim();
            String cardType = book.xpath("//td[3]/text()").toString().trim();

            bookInfoDO.setName(name);
            bookInfoDO.setCardNo(cardNo);
            bookInfoDO.setCardType(cardType);

            bookInfoDOList.add(bookInfoDO);
        }
        page.putField("bookInfoDOList", bookInfoDOList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
