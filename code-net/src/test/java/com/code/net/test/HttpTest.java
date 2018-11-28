package com.code.net.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

    String url = "https://s.creditcard.ecitic.com/citiccard/lottery-gateway-pay/pointLottery.do";

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


}
