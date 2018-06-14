package com.cui.code.test;

import com.mobike.pandora.client.model.result.Result;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件以及IO测试
 * Created by cuishixiang on 2017-11-07.
 */
public class FileIOTest {

    @Test
    public void testFile() {
        File file = new File(".");
        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent());
        System.out.println(file.exists());
        System.out.println(file.canRead());
        System.out.println(file.canWrite());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(file.isAbsolute());
        System.out.println(file.lastModified());
        System.out.println(file.length());

        Arrays.stream(file.list()).forEach(System.out::println);

        System.out.println("separator：" + File.separatorChar);
        System.out.println("pathSeparator：" + File.pathSeparator);
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * 发券-营销中心
     */
    @Test
    public void testSendCoupon() {
        long timeMillis = System.currentTimeMillis();
        System.out.println("begin time：" + new Date());
        RestTemplate restTemplate = new RestTemplate();
        Path path = Paths.get("/Users/cuiswing/Documents/骑行挑战/六个核桃", "六个核桃骑行挑战level2中奖用户.txt");
        String level1ActivityId = "act_1527665302044582881";
        String level2ActivityId = "act_1527665524922842583";
        try {
            Stream<String> lines = Files.lines(path);
            //System.out.println(lines.count());
            int successNum = 0;
            Integer failNum = 0;
            List<String> userIds = lines.collect(Collectors.toList());
            List<String> successList = new ArrayList<>(700);
            List<String> failList = new ArrayList<>();
            //pandora-coupon 服务
            List<String> urls = new ArrayList<>();
            urls.add("http://10.30.111.142:9422");
            urls.add("http://10.30.118.144:9422");
            urls.add("http://10.30.104.141:9422");
            urls.add("http://10.30.154.134:9422");
            urls.add("http://10.30.156.129:9422");
            urls.add("http://10.30.159.130:9422");

            for (int i = 0; i < userIds.size(); i++) {
                String url = urls.get(i % 6);
                String userId = userIds.get(i);

                Result result = restTemplate.getForObject(url + "/couponv2/receivebyactivity?activityId={activityId}&userId={userId}", Result.class, level2ActivityId, userId);
                if (result.getCode().equals("0")) {
                    successNum++;
                    successList.add(userId);
                } else {
                    failNum++;
                    failList.add(userId);
                }
            }
            System.out.println("totalNum：" + userIds.size());
            System.out.println("successNum：" + successNum);
            System.out.println("failNum：" + failNum);
            System.out.println("failed：" + failList);
            System.out.println("end time：" + new Date());
            System.out.println("cost TimeMillis：" + (System.currentTimeMillis() - timeMillis));
        } catch (IOException e) {
            System.out.println("文件读取失败");
            e.printStackTrace();
        }
    }
}
