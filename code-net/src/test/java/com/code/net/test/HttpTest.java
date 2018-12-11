package com.code.net.test;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http基础测试
 *
 * @author cuishixiang
 * @date 2018-11-26
 */
public class HttpTest {

    private RestTemplate restTemplate = new RestTemplate();

    private String url = "https://s.creditcard.ecitic.com/citiccard/lottery-gateway-pay/pointLottery.do";

    @Test
    public void testGet() {

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Object response = responseEntity.getBody();
        System.out.println(response);
    }


    /**
     * 中信信用卡的抽奖接口，这个接口处理的很慢啊，多弄几个线程并发一上来就"0000059——扣减积分失败"，这不行啊……
     * 1.5w分都抽完了，直到最后的"0000060——积分不足"，一个奖都没有中……😌☹️ 再也不相信抽奖了……
     */
    @Test
    public void testPost() {
        ExecutorService executorService = Executors.newWorkStealingPool();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "s.creditcard.ecitic.com");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Cache-Control", "no-cache");
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("Origin", "https://s.creditcard.ecitic.com");
        httpHeaders.add("x-requested-with", "XMLHttpRequest");
        httpHeaders.add("deviceInfo", "undefined");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36 DKKJ/4.1.0/DKKJ_TOWER_1.0 DKKJ_TOWER_1.0");
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Referer", "https://s.creditcard.ecitic.com/citiccard/lotteryfrontend/IntegralLottery.html");
        httpHeaders.add("Accept-Language", "zh-CN,en-US;q=0.9");
        httpHeaders.add("Cookie", "");

        JSONObject request = new JSONObject();
        request.put("actId", "JFCJHD");
        String requestJSON = request.toString();
        System.out.println(requestJSON);

        HttpEntity<Object> httpEntity = new HttpEntity<>(requestJSON, httpHeaders);

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 50; i++) {
            Runnable task = () -> {
                String responseBody = restTemplate.postForObject(url, httpEntity, String.class);
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                int j = count.incrementAndGet();
                if (jsonObject.getString("resultCode").equals("0000006")) {
                    System.out.println(j + "：" + jsonObject.getString("resultDesc"));
                } else {
                    System.out.print(j + "：" + jsonObject.getString("resultCode") + "——");
                    System.out.println(jsonObject.getString("resultDesc"));
                }
            };
            executorService.submit(task);
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("所有线程执行完成");
                break;
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 京津冀旅游年卡景区预定 测试
     */
    @Test
    public void testBookTicket() {
        // 天津的相声：9   奥林匹克塔：7
        String subscribeId = "9";
        // 预约日期
        String bookDate = "2018-12-14";
        // 预登陆后的JSESSIONID
        String JSESSIONID = "自己登陆后的jsessionid";
        String cardNo = "填写自己的卡号";

        System.out.println(new Date());
        int count = 0;
        while (true) {
            if (++count % 100 == 0) {
                System.out.println("retry count：" + count);
                System.out.println(new Date());
            }
            try {
                String subscribeCalendarId = getSubscribeCalendarId(subscribeId, bookDate, JSESSIONID);
                if (subscribeCalendarId != null) {
                    boolean result = lynkBook(subscribeId, subscribeCalendarId, cardNo, JSESSIONID);
                    if (result) {
                        System.out.println(count + "：预约成功，退出循环");
                        System.out.println(new Date());
                        break;
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取可预订日期的id
     *
     * @param subscribeId 景区id
     * @param bookDate    预定日期
     * @param JSESSIONID  登陆后的JSESSIONID
     * @return 日期的id
     */
    private String getSubscribeCalendarId(String subscribeId, String bookDate, String JSESSIONID) {
        String tianjinURL = "http://zglynk.com/ITS/itsApp/goSubscribe.action?subscribeId=" + subscribeId;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + JSESSIONID);
        HttpEntity request = new HttpEntity(httpHeaders);

        String responseString = restTemplate.postForObject(tianjinURL, request, String.class);
        Document document = Jsoup.parse(responseString);
        Elements tables = document.getElementsByClass("ticket-info mart20");
        Element table = tables.get(0);
        Elements trs = table.getElementsByTag("tr");
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            Element date = tds.get(0);
            if (bookDate.equals(date.text())) {
                Element bookTd = tds.get(2);
                String bookText = bookTd.text();
                if (bookText.startsWith("可预约")) {
                    Elements input = bookTd.getElementsByTag("input");
                    return input.attr("value");
                }
            }
        }
        return null;
    }

    /**
     * 京津冀旅游年卡景区预约提交
     */
    private boolean lynkBook(String subscribeId, String subscribeCalendarId, String cardNo, String JSESSIONID) {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("1", "预约成功");
        statusMap.put("2", "预约失败，请重试！");
        statusMap.put("3", "超预约规定次数");
        statusMap.put("4", "卡不在允许预约范围内");
        statusMap.put("5", "卡不在允许预约范围内");
        statusMap.put("6", "超过总次数，当天景区预约已满");

        String bookURL = "http://zglynk.com/ITS/itsApp/saveUserSubscribeInfo.action";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "zglynk.com");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Cache-Control", "max-age=0");
        httpHeaders.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,image/wxpic,image/sharpp,image/apng,image/tpg,*/*;q=0.8");
        httpHeaders.add("Origin", "http://zglynk.com");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044403 Mobile Safari/537.36 MMWEBID/1085 MicroMessenger/6.7.3.1360(0x2607033A) NetType/WIFI Language/zh_CN Process/tools");
        httpHeaders.add("Referer", "http://zglynk.com/ITS/itsApp/goSubscribe.action?subscribeId=" + subscribeId);
        httpHeaders.add("Accept-Language", "zh-CN,en-US;q=0.8");
        httpHeaders.add("Cookie", "JSESSIONID=" + JSESSIONID);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("subscribeId", subscribeId);
        parameter.add("subscribeCalendarId", subscribeCalendarId);
        parameter.add("cardNo_792826", cardNo);
        parameter.add("cardType_792826", "1");
        parameter.add("cardId", "792826#" + cardNo);

        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        int count = 0;
        for (int i = 0; i < 3; i++) {
            try {
                String responseBody = restTemplate.postForObject(bookURL, request, String.class);
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                if ("1".equals(jsonObject.getString("status"))) {
                    return true;
                } else {
                    System.out.println("fail：" + ++count + "——" + responseBody);
                    System.out.println(statusMap.getOrDefault(jsonObject.getString("status"), "预约失败，请重试！"));
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;

        // 成功：
        //"{\n" +
        //        "\t\"status\": \"1\",\n" +
        //        "\t\"message\": \"成功\",\n" +
        //        "\t\"id\": \"76198\"\n" +
        //        "}"
    }
}
