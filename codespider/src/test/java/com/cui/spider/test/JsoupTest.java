package com.cui.spider.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * jsoup解析html文档
 * Created by cuishixiang on 2017-10-24.
 */
public class JsoupTest {

    @Test
    public void testBaidu() {
        String baiduUrl = "https://www.baidu.com/";
        String cuiUrl = "https://cuishixiang.cn";
        try {
            Document document = Jsoup.connect(baiduUrl)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .referrer("https://www.baidu.com")
                    .get();
            System.out.println(document.title());
//            System.out.println(document.head());

            //百度的logo
//            Element logoElement = document.getElementById("s_lg_img");
//            System.out.println(logoElement);

//            Elements elements = document.select("img");
//            System.out.println(elements);

            System.out.println(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZhihuRecommend() {
        String zhihuUrl = "https://www.zhihu.com/explore/recommendations";
        try {
            Document document = Jsoup.connect(zhihuUrl)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .referrer("https://www.zhihu.com")
                    .get();
            Elements questions = document.select("a.question_link");
            System.out.println(questions);
            System.out.println(questions.text());
            System.out.println(questions.eachText());
            String href = questions.get(0).attr("abs:href");
            System.out.println(href);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
