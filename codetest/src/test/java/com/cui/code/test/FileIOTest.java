package com.cui.code.test;

import com.cui.code.test.dto.GaodeResultDTO;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
     * WGS84坐标转高德坐标
     */
    @Test
    public void testWGS84ToGaode() {
        String gaodeURL = "http://restapi.amap.com/v3/assistant/coordinate/convert";
        String key = "4248308fc11d107a2dd6c38bc59f3c08";
        String coordsys = "gps";
        Long currentTimeMillis = System.currentTimeMillis();

        try {
            List<String> lines = Files.readAllLines(Paths.get("/Users/cuiswing/Documents", "陆家嘴单车区域数据0514.csv"));
            List<String> importLines = lines.stream().filter(line -> line.endsWith("米级")).collect(Collectors.toList());

            System.out.println("total line：" + lines.size());
            System.out.println("---------------------");
            System.out.println("import line：" + importLines.size());

            Map<Integer, Integer> countMap = new HashMap<>();
            AtomicInteger atomicInteger = new AtomicInteger(0);
            List<String> fails = new ArrayList<>();

            File file = new File("/Users/cuiswing/Documents/陆家嘴MPL导入数据.csv");
            file.delete();
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            importLines.forEach(line -> {
                String[] split = line.split(",");
                countMap.putIfAbsent(split.length, 0);
                countMap.put(split.length, countMap.get(split.length) + 1);
                if (split.length == 9) {
                    System.out.println(line);
                }

                String poiName = split[3];
                String lng = split[4];
                String lat = split[5];
                String locations = lng + "," + lat;

                RestTemplate restTemplate = new RestTemplate();

                atomicInteger.incrementAndGet();
                GaodeResultDTO gaodeResultDTO = restTemplate.getForObject(gaodeURL + "?key={key}&locations={locations}&coordsys={coordsys}&output=JSON", GaodeResultDTO.class, key, locations, coordsys);
                if (1 == gaodeResultDTO.getStatus()) {
                    String poiData = MessageFormat.format("{0},{1},{2}", "5", poiName, gaodeResultDTO.getLocations());
                    try {
                        bufferedWriter.write(poiData);
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        fails.add(line);
                        e.printStackTrace();
                    }
                } else {
                    fails.add(line);
                }
                if (atomicInteger.get() % 100 == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(countMap);
            System.out.println("fails count :" + fails.size());
            System.out.println("fails :" + fails);
            bufferedWriter.close();
            System.out.println("cost ms: " + (System.currentTimeMillis() - currentTimeMillis));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
