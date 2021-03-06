package com.code.net.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cui.code.net.model.BookCardInfo;
import com.cui.code.net.model.CardInfo;
import com.cui.code.net.model.SubscribeIdEnum;
import com.cui.code.net.model.SuccessInfo;
import com.cui.code.net.util.YamlUtil;
import com.cui.util.mail.MailConfig;
import com.cui.util.mail.MailUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * http基础测试
 *
 * @author cuishixiang
 * @date 2018-11-26
 */
public class HttpTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpTest.class);

    private static SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    private static final Map<String, String> statusMap = new HashMap<>();
    private static final String EMAIL_CONFIG = "/config/email.properties";
    private static final MailConfig MAIL_CONFIG = new MailConfig(EMAIL_CONFIG);
    private static final MailUtil mailUtil = new MailUtil(MAIL_CONFIG);

    static {
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(5000);

        statusMap.put("1", "预约成功");
        statusMap.put("2", "预约失败，请重试！");
        statusMap.put("3", "超预约规定次数");
        statusMap.put("4", "卡不在允许预约范围内");
        statusMap.put("5", "卡不在允许预约范围内");
        statusMap.put("6", "超过总次数，当天景区预约已满");
        statusMap.put("7", "预约失败，读取预约数量数据失败，请联系管理员");
        statusMap.put("8", "预约失败，读取预约数量数据失败，请联系管理员");
        statusMap.put("9", "卡过期");
        statusMap.put("10", "预约名额不足，请重新选择卡号");
    }

    private RestTemplate restTemplate = new RestTemplate(requestFactory);

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
        httpHeaders.add(HttpHeaders.HOST, "s.creditcard.ecitic.com");
        httpHeaders.add(HttpHeaders.PRAGMA, "no-cache");
        httpHeaders.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
        httpHeaders.add(HttpHeaders.ORIGIN, "https://s.creditcard.ecitic.com");
        httpHeaders.add("x-requested-with", "XMLHttpRequest");
        httpHeaders.add("deviceInfo", "undefined");
        httpHeaders.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36 DKKJ/4.1.0/DKKJ_TOWER_1.0 DKKJ_TOWER_1.0");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        httpHeaders.add(HttpHeaders.REFERER, "https://s.creditcard.ecitic.com/citiccard/lotteryfrontend/IntegralLottery.html");
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,en-US;q=0.9");
        httpHeaders.add(HttpHeaders.COOKIE, "");

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


    public static void main(String[] args) {
        HttpTest httpTest = new HttpTest();
        httpTest.startBookTicket(null);
    }

    /**
     * 新版 idea 不能同时运行多个相同的单元测试，改为多个 junit 来传递参数，决定使用哪些配置文件
     */
    @Test
    public void testBook1() {
        startBookTicket(YamlUtil.LY_CONFIG_FILE);
    }

    /**
     * 新版 idea 不能同时运行多个相同的单元测试，改为多个 junit 来传递参数，决定使用哪些配置文件
     */
    @Test
    public void testBook2() {
        startBookTicket(YamlUtil.LY_CONFIG_FILE2);
    }

    @Test
    public void testBook3() {
        startBookTicket(YamlUtil.LY_CONFIG_FILE3);
    }

    @Test
    public void testBook4() {
        startBookTicket(YamlUtil.LY_CONFIG_FILE4);
    }

    /**
     * 启动京津冀旅游年卡景区预约
     *
     * @param configFile 指定配置文件
     */
    private void startBookTicket(String configFile) {
        BookCardInfo bookCardInfo = YamlUtil.getBookCardInfo(configFile);
        logger.info("预约配置信息：{}", bookCardInfo);
        // 校验数据
        boolean validation = validation(bookCardInfo);
        List<CardInfo> cardInfoList = getCardInfo(bookCardInfo);
        bookCardInfo.setCardInfoList(cardInfoList);

        logger.info("获取预约人信息后：{}", bookCardInfo);
        int count = 0;
        while (true) {
            if (++count % 100 == 0) {
                logger.info("retry count：{}", count);
            }
            try {
                boolean subscribeCalendarId = getSubscribeCalendarId(bookCardInfo);
                if (subscribeCalendarId) {
                    logger.info("bookCardInfo:{}", bookCardInfo);
                    boolean result = lynkBook(bookCardInfo);
                    if (result) {
                        logger.info("第{}次预约成功", count);
                        if (bookCardInfo.isEmailNotice()) {
                            String name = SubscribeIdEnum.getSubscribeIdEnumById(bookCardInfo.getSubscribeId()).name();
                            String subject = MessageFormat.format("景区预约成功——{0}", name);
                            String content = MessageFormat.format("<h3>预约信息：</h3><ol><li>预约卡号：{0}</li><li>预约景区：{1}</li>" +
                                            "<li>预约日期：{2}</li><li>邮件发送时间：{3}</li></ol>" +
                                            "<h4>其他信息：</h4><p>预约详情：{4}</p>",
                                    bookCardInfo.getCardInfoList().stream().map(CardInfo::getCardNo).collect(Collectors.joining(";")), name,
                                    bookCardInfo.getBookDate(), new Date(), JSON.toJSONString(bookCardInfo));
                            mailUtil.sendMailByConfig(subject, content);
                        }
                        if (bookCardInfo.getCardInfoTempList().isEmpty()) {
                            return;
                        } else {
                            bookCardInfo.setCardInfoList(bookCardInfo.getCardInfoTempList());
                            bookCardInfo.setCardInfoTempList(Collections.emptyList());
                            continue;
                        }
                    }
                }
                if (System.currentTimeMillis() >= bookCardInfo.getEndTime().getTime()) {
                    logger.info("当前时间已超过预约截止时间：{}，停止抢票。", bookCardInfo.getEndTime());
                    mailUtil.sendMailByConfig("停止抢票", "当前时间已超过预约截止时间：" + bookCardInfo.getEndTime() + "，停止抢票。如有需要请重新设置后再次启动。");
                    return;
                }

                if (bookCardInfo.isTiming() && System.currentTimeMillis() < bookCardInfo.getTimingStartTime().getTime()) {
                    Thread.sleep(1000 * 60);
                } else {
                    int sleepTime = (int) (1000 + Math.random() * 500);
                    Thread.sleep(sleepTime);
                }
            } catch (Exception e) {
                logger.error("抢票异常", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    logger.error("线程异常", e);
                }
            }
        }
    }

    /**
     * 数据校验：日期格式对不对？开启定时抢票功能后的开抢时间点是否在当前时间之后？预约截止时间是否在当前时间之后，在开抢定时时间之后？
     *
     * @param bookCardInfo 预约配置数据
     * @return 校验通过或未通过
     */
    private boolean validation(BookCardInfo bookCardInfo) {
        String maxDate = Collections.max(bookCardInfo.getBookDateList());
        LocalDate bookDate = LocalDate.parse(maxDate);
        // 最晚刷票时间点：预约时间点加上24小时（预约日期当天的最后一刻）
        LocalDateTime maxEndDateTime = LocalDateTime.of(bookDate, LocalTime.MAX);
        Date maxEndDate = Date.from(maxEndDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Date now = new Date();
        if (bookCardInfo.isTiming()) {
            if (bookCardInfo.getTimingStartTime().before(now)) {
                // 懒得包装自定义异常了，就直接甩锅吧
                throw new RuntimeException("定时开抢时间(" + bookCardInfo.getTimingStartTime() + ")不能比当前时间(" + now + ")还早，" +
                        "建议：定时开抢时间设置为放票时间前5~10分钟。");
            } else if (bookCardInfo.getTimingStartTime().after(maxEndDate)) {
                throw new RuntimeException("定时开抢时间(" + bookCardInfo.getTimingStartTime() + ")不能比预约日期(" + maxEndDate + ")还晚，" +
                        "建议：定时开抢时间设置为放票时间前5~10分钟。");
            }
        }


        if (bookCardInfo.getEndTime().before(now)) {
            throw new RuntimeException("预约截止时间(" + bookCardInfo.getEndTime() + ")不能比当前时间(" + now + ")还早，" +
                    "建议：预约截止时间设置为预约日期前几个小时并且不应超过预约日期当天的24点，请仔细考虑后再设置。");
        } else if (bookCardInfo.getEndTime().after(maxEndDate)) {
            logger.info("预约截止时间({})已超过最大截止时间点,已自动修正为最大截止时间点({})。", bookCardInfo.getEndTime(), maxEndDate);
            bookCardInfo.setEndTime(maxEndDate);
        }

        return true;
    }

    /**
     * 获取预约人相关信息
     */
    private List<CardInfo> getCardInfo(BookCardInfo bookCardInfo) {
        Elements tables = getTables(bookCardInfo);

        //解析cardId
        Element cardTable = tables.get(1);
        Elements cardNoTrs = cardTable.getElementsByTag("tr");
        List<CardInfo> cardInfoList = new ArrayList<>();
        for (Element cardNoTr : cardNoTrs) {
            String cardName = cardNoTr.getElementsByTag("td").get(0).getElementsByTag("span").get(0).text().trim();
            Element td = cardNoTr.getElementsByTag("td").get(1);
            String cardNo = td.text().trim();
            if (bookCardInfo.getCardNoList().contains(cardNo)) {
                // cardNo_XXXXXX
                String name = td.child(0).attr("name");
                String cardId = name.substring(7);
                String idCard = td.child(2).attr("value");
                CardInfo cardInfo = new CardInfo(cardName, cardId, cardNo, idCard);
                cardInfoList.add(cardInfo);
                bookCardInfo.getCardNoList().remove(cardNo);
            }
        }
        if (cardInfoList.isEmpty()) {
            throw new RuntimeException("未获取到配置文件中的任何预约卡信息：" + bookCardInfo.getCardNoList());
        }
        if (!bookCardInfo.getCardNoList().isEmpty()) {
            logger.warn("未获取到配置文件中的部分预约卡信息：{}", bookCardInfo.getCardNoList());
        }
        return cardInfoList;
    }

    /**
     * 获取可预订日期的id
     *
     * @param bookCardInfo 预订卡信息
     * @return 日期的id
     */
    private boolean getSubscribeCalendarId(BookCardInfo bookCardInfo) {
        Elements tables = getTables(bookCardInfo);
        // 解析日期id
        Element table = tables.get(0);
        Elements trs = table.getElementsByTag("tr");
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            Element date = tds.get(0);
            // 多个日期只要有任何一个满足即可预约
            if (bookCardInfo.getBookDateList().contains(date.text())) {
                Element bookTd = tds.get(2);
                String bookText = bookTd.text();
                if (bookText.startsWith("可预约")) {
                    Elements input = bookTd.getElementsByTag("input");
                    String subscribeCalendarId = input.attr("value");
                    bookCardInfo.setSubscribeCalendarId(subscribeCalendarId);
                    bookCardInfo.setBookDate(date.text());
                    return true;
                }
            }
        }
        return false;
    }

    private Elements getTables(BookCardInfo bookCardInfo) {
        String getSubscribeURL = "http://zglynk.com/ITS/itsApp/goSubscribe.action?subscribeId=" + bookCardInfo.getSubscribeId();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + bookCardInfo.getJSESSIONID());
        HttpEntity request = new HttpEntity(httpHeaders);

        String responseString = restTemplate.postForObject(getSubscribeURL, request, String.class);
        // 未登录
        if (responseString.contains("window.open ('/ITS/itsApp/login.jsp','_top')")) {
            lynkLogin(bookCardInfo.getJSESSIONID());
            return null;
        } else if (responseString.contains("<html>\n" +
                "<script>\n" +
                "window.open ('/ITS/itsApp/loginAuthorization.jsp','_top')\n" +
                "</script>\n" +
                "</html>\n")) {
            logger.error("微信授权已失效，请重新抓取sessionId");
            return null;
        }
        Document document = Jsoup.parse(responseString);
        Elements tables = document.getElementsByClass("ticket-info mart20");
        return tables;
    }

    /**
     * 京津冀旅游年卡景区预约提交
     */
    private boolean lynkBook(BookCardInfo bookCardInfo) {
        String bookURL = "http://zglynk.com/ITS/itsApp/saveUserSubscribeInfo.action";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.HOST, "zglynk.com");
        httpHeaders.add(HttpHeaders.PRAGMA, "no-cache");
        httpHeaders.add(HttpHeaders.CACHE_CONTROL, "max-age=0");
        httpHeaders.add(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,image/wxpic,image/sharpp,image/apng,image/tpg,*/*;q=0.8");
        httpHeaders.add(HttpHeaders.ORIGIN, "http://zglynk.com");
        httpHeaders.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Linux; Android 8.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044403 Mobile Safari/537.36 MMWEBID/1085 MicroMessenger/6.7.3.1360(0x2607033A) NetType/WIFI Language/zh_CN Process/tools");
        httpHeaders.add(HttpHeaders.REFERER, "http://zglynk.com/ITS/itsApp/goSubscribe.action?subscribeId=" + bookCardInfo.getSubscribeId());
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,en-US;q=0.8");
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + bookCardInfo.getJSESSIONID());

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("subscribeId", bookCardInfo.getSubscribeId());
        parameter.add("subscribeCalendarId", bookCardInfo.getSubscribeCalendarId());
        for (CardInfo cardInfo : bookCardInfo.getCardInfoList()) {
            parameter.add("cardNo_" + cardInfo.getCardId(), cardInfo.getCardNo());
            parameter.add("cardType_" + cardInfo.getCardId(), "1");
            parameter.add("userIdCard_" + cardInfo.getCardId(), cardInfo.getIdCard());
            parameter.add("cardId", cardInfo.getCardId() + "#" + cardInfo.getCardNo() + "#" + cardInfo.getIdCard());
        }

        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        int count = 0;
        for (int i = 0; i < 3; i++) {
            try {
                // 成功响应：{"status":"1","message":"成功"}
                String responseBody = restTemplate.postForObject(bookURL, request, String.class);
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                String status = jsonObject.getString("status");
                if ("1".equals(status)) {
                    logger.info("responseBody:{}", responseBody);
                    // 预约成功后立即查询预约列表，可能查不到数据，考虑换成异步线程，等待一分钟然后再去查询并发送邮件
                    setSuccessInfo(bookCardInfo);
                    return true;
                } else {
                    logger.info("fail:{}——{}", ++count, responseBody);
                    logger.info("fail reason:{}", statusMap.getOrDefault(status, "预约失败，请重试！"));
                    // 部分提交：由于不知道票数余量有多少张，所以逐张提交
                    if ("10".equals(status)) {
                        CardInfo firstSubmitCardInfo = bookCardInfo.getCardInfoList().remove(0);
                        bookCardInfo.setCardInfoTempList(bookCardInfo.getCardInfoList());
                        bookCardInfo.setCardInfoList(Collections.singletonList(firstSubmitCardInfo));
                        return false;
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                logger.error("{}本次预约失败", ++count, e);
            }
        }
        return false;
    }

    /**
     * 京津冀旅游年卡登陆
     */
    private void lynkLogin(String JSESSIONID) {
        String loginURL = "http://zglynk.com/ITS/itsApp/login.action";
        String userPhone = "手机号";
        String loginPassword = "密码";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.HOST, "zglynk.com");
        httpHeaders.add(HttpHeaders.PRAGMA, "no-cache");
        httpHeaders.add(HttpHeaders.CACHE_CONTROL, "max-age=0");
        httpHeaders.add(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,image/wxpic,image/sharpp,image/apng,image/tpg,*/*;q=0.8");
        httpHeaders.add(HttpHeaders.ORIGIN, "http://zglynk.com");
        httpHeaders.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Linux; Android 8.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044403 Mobile Safari/537.36 MMWEBID/1085 MicroMessenger/6.7.3.1360(0x2607033A) NetType/WIFI Language/zh_CN Process/tools");
        httpHeaders.add(HttpHeaders.REFERER, "http://zglynk.com/ITS/itsApp/login.jsp");
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,en-US;q=0.8");
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + JSESSIONID);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("userPhone", userPhone);
        parameter.add("loginPassword", loginPassword);
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        String responseBody = restTemplate.postForObject(loginURL, request, String.class);
        logger.info("登陆结果:{}", responseBody);
    }

    /**
     * 获取预约成功后的id 信息
     */
    private void setSuccessInfo(BookCardInfo bookCardInfo) {
        try {
            List<String> idList = getMySubscribeIdList(bookCardInfo);
            List<SuccessInfo> successInfoList = createSuccessInfoList(bookCardInfo.getJSESSIONID(), idList);
            bookCardInfo.setSuccessInfoList(successInfoList);
        } catch (Exception e) {
            logger.error("获取预约成功的 id 信息出现异常", e);
        }
    }

    /**
     * 将成功的id 和姓名、卡号关联
     */
    private List<SuccessInfo> createSuccessInfoList(String JSESSIONID, List<String> idList) {
        String goViewUserSubscribeURL = "http://zglynk.com/ITS/itsApp/goViewUserSubscribe.action?id=";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + JSESSIONID);
        HttpEntity request = new HttpEntity(httpHeaders);

        List<SuccessInfo> successInfoList = new ArrayList<>();
        for (String id : idList) {
            String idURL = goViewUserSubscribeURL + id;
            String responseString = restTemplate.postForObject(idURL, request, String.class);
            Document document = Jsoup.parse(responseString);
            Elements tables = document.getElementsByClass("ticket-info mart20");
            Element table = tables.get(0);
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                String name = tds.get(0).text();
                String cardNo = tds.get(1).text();
                successInfoList.add(new SuccessInfo(id, name, cardNo));
            }
        }
        return successInfoList;
    }

    /**
     * 京津冀旅游年卡景区预定 ：取消
     */
    @Test
    public void testCancelBookTicket() {
        String JSESSIONID = "自己登陆后的jsessionid";
        //要取消的景区
        String cancelName = SubscribeIdEnum.奥林匹克塔.name();

        System.out.println(new Date());
        int count = 0;
        while (true) {
            try {
                String bookId = getMySubscribeId(JSESSIONID, cancelName);
                if (bookId != null) {
                    System.out.println("cancel book id：" + bookId);
                    boolean cancelResult = cancelBookTicket(JSESSIONID, bookId);
                    if (cancelResult) {
                        System.out.println(count + "：取消成功，退出循环");
                        System.out.println(new Date());
                        break;
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取已预订景区的id
     *
     * @param JSESSIONID 登陆后的JSESSIONID
     * @param cancelName 待取消景区的名称
     * @return
     */
    private String getMySubscribeId(String JSESSIONID, String cancelName) {
        String mySubscribeListURL = "http://zglynk.com/ITS/itsApp/goMySubscribeList.action";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + JSESSIONID);
        HttpEntity request = new HttpEntity(httpHeaders);

        String responseString = restTemplate.postForObject(mySubscribeListURL, request, String.class);
        Document document = Jsoup.parse(responseString);

        Element ul = document.select("ul.jq-yu-list.p-top30").first();
        Elements lis = ul.getElementsByTag("li");
        for (Element li : lis) {
            Element aNode = li.getElementsByTag("a").first();
            String ticketTitle = aNode.select("p.font34").text();
            String bookStatusDescription = aNode.select("p.mart30.color-01b584").text();
            if (ticketTitle.contains(cancelName) && bookStatusDescription.contains("预约成功")) {
                String href = aNode.attr("href");
                String bookId = href.substring("goViewUserSubscribe.action?id=".length());
                return bookId;
            }
        }
        return null;
    }

    /**
     * 获取已预订景区的id 列表
     */
    private List<String> getMySubscribeIdList(BookCardInfo bookCardInfo) {
        String mySubscribeListURL = "http://zglynk.com/ITS/itsApp/goMySubscribeList.action";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + bookCardInfo.getJSESSIONID());
        HttpEntity request = new HttpEntity(httpHeaders);

        String responseString = restTemplate.postForObject(mySubscribeListURL, request, String.class);
        Document document = Jsoup.parse(responseString);

        List<String> idList = new ArrayList<>();
        Element ul = document.select("ul.jq-yu-list.p-top30").first();
        Elements lis = ul.getElementsByTag("li");
        for (Element li : lis) {
            Element aNode = li.getElementsByTag("a").first();
            String ticketTitle = aNode.select("p.font34").text();
            String bookStatusDescription = aNode.select("p.mart30").text();
            if (ticketTitle.contains(bookCardInfo.getSubscribeName()) && bookStatusDescription.contains(bookCardInfo.getBookDate())
                    && bookStatusDescription.contains("预约成功")) {
                String href = aNode.attr("href");
                String bookId = href.substring("goViewUserSubscribe.action?id=".length());
                idList.add(bookId);
            }
        }
        return idList;
    }

    /**
     * 取消预订
     *
     * @param JSESSIONID 登陆后的JSESSIONID
     * @param bookId
     */
    private boolean cancelBookTicket(String JSESSIONID, String bookId) {
        String cancelURL = "http://zglynk.com/ITS/itsApp/cancelUserSubscribeInfo.action";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + JSESSIONID);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", bookId);
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        int count = 0;
        for (int i = 0; i < 3; i++) {
            try {
                String responseBody = restTemplate.postForObject(cancelURL, request, String.class);
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                if ("1".equals(jsonObject.getString("status"))) {
                    System.out.println(responseBody);
                    return true;
                } else {
                    System.out.println("cancel fail：" + ++count + "——" + responseBody);
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
