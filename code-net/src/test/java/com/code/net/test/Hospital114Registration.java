package com.code.net.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.net.test.dto.DutyCalendar;
import com.code.net.test.dto.DutyDTO;
import com.code.net.test.dto.DutyDoctorInfo;
import com.code.net.test.dto.HospitalCalendarDTO;
import com.cui.code.net.model.hospital.DutyTime;
import com.cui.code.net.model.hospital.HospitalBookInfo;
import com.cui.code.net.util.YamlUtil;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * 北京114挂号平台
 * 直接运行junitTest时在idea 控制台无法从键盘录入任何信息,只能用main函数调用test方法
 *
 * @author cuiswing
 * @date 2019-05-18
 */
public class Hospital114Registration {
    private static final Logger logger = LoggerFactory.getLogger(Hospital114Registration.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private RestTemplate restTemplate = new RestTemplate();

    // 登陆后的cookie信息
    private String cookies = "";
    private static final int NO_LOGIN = 2009;


    public static void main(String[] args) {
        new Hospital114Registration().testRegistration();
    }

    // 预约挂号
    @Test
    public void testRegistration() {
        HospitalBookInfo hospitalBookInfo = YamlUtil.getHospitalBookInfo();

        System.out.println("启动时间：" + new Date());
        int count = 0;
        while (true) {
            if (++count % 100 == 0) {
                System.out.println("retry count：" + count);
                System.out.println(new Date());
            }
            try {
                boolean hasNumber = hasNumber(hospitalBookInfo);
                // 没有号就等一秒多再看看
                if (!hasNumber) {
                    int sleepTime = (int) (1000 + Math.random() * 500);
                    Thread.sleep(sleepTime);
                    continue;
                }

                DutyDTO dutyDTO = getDoctorIdAndDutySourceId(hospitalBookInfo);
                if (dutyDTO != null && dutyDTO.getCode() == 1) {
                    Map<Integer, List<DutyDoctorInfo>> data = dutyDTO.getData();
                    System.out.println("上午有号可挂的值班医生：");
                    List<DutyDoctorInfo> morningDutyDoctorInfos = data.get(1);
                    if (morningDutyDoctorInfos != null) {
                        morningDutyDoctorInfos.removeIf(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() <= 0);
                    }
                    System.out.println(morningDutyDoctorInfos);

                    System.out.println("下午有号可挂的值班医生：");
                    List<DutyDoctorInfo> afternoonDutyDoctorInfos = data.get(2);
                    if (afternoonDutyDoctorInfos != null) {
                        afternoonDutyDoctorInfos.removeIf(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() <= 0);
                    }
                    System.out.println(afternoonDutyDoctorInfos);

                    System.out.println("晚上的值班医生：");
                    List<DutyDoctorInfo> otherDutyDoctorInfos = data.get(4);
                    if (otherDutyDoctorInfos != null) {
                        otherDutyDoctorInfos.removeIf(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() <= 0);
                    }
                    System.out.println(otherDutyDoctorInfos);


                    String doctorId = null;
                    int dutySourceId = 0;
                    Scanner scanner = new Scanner(System.in);

                    // 有dutySourceId 就直接查
                    if (!StringUtils.isEmpty(hospitalBookInfo.getDutySourceId())) {
                        int finalDutySourceId = Integer.parseInt(hospitalBookInfo.getDutySourceId());
                        for (List<DutyDoctorInfo> dutyDoctorInfos : data.values()) {
                            Optional<DutyDoctorInfo> optionalDutyDoctorInfo = dutyDoctorInfos.stream()
                                    .filter(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() > 0
                                            && dutyDoctorInfo.getDutySourceId() == finalDutySourceId).findFirst();
                            if (optionalDutyDoctorInfo.isPresent()) {
                                doctorId = optionalDutyDoctorInfo.get().getDoctorId();
                                break;
                            }
                        }
                    } else if (!StringUtils.isEmpty(hospitalBookInfo.getDoctorName())) {
                        // 没有 dutySourceId 但是有医生名字时，就筛选全部姓名
                        // 如果确定了上下午还是晚上，则只从该时段中筛查
                        if (!StringUtils.isEmpty(hospitalBookInfo.getDutyTime())) {
                            DutyTime dutyTime = DutyTime.getDutyTimeByTime(hospitalBookInfo.getDutyTime());
                            if (dutyTime != null) {
                                List<DutyDoctorInfo> dutyDoctorInfos = data.get(dutyTime.getCode());
                                Optional<DutyDoctorInfo> firstOptional = dutyDoctorInfos.stream().filter(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() > 0 &&
                                        dutyDoctorInfo.getDoctorName().trim().equals(hospitalBookInfo.getDoctorName()))
                                        .findFirst();
                                if (firstOptional.isPresent()) {
                                    doctorId = firstOptional.get().getDoctorId();
                                }
                            }
                        } else {
                            // 否则全部筛查并选第一个
                            for (List<DutyDoctorInfo> dutyDoctorInfos : data.values()) {
                                Optional<DutyDoctorInfo> firstOptional = dutyDoctorInfos.stream().filter(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() > 0 &&
                                        dutyDoctorInfo.getDoctorName().trim().equals(hospitalBookInfo.getDoctorName()))
                                        .findFirst();
                                if (firstOptional.isPresent()) {
                                    doctorId = firstOptional.get().getDoctorId();
                                    break;
                                }
                            }
                        }
                    } else {
                        // 如果都没有选择则直接输入吧
                        System.out.println("选择医生，输入dutySourceId：");
                        dutySourceId = scanner.nextInt();
                        scanner.nextLine();

                        for (List<DutyDoctorInfo> dutyDoctorInfos : data.values()) {
                            int finalDutySourceId = dutySourceId;
                            Optional<DutyDoctorInfo> optionalDutyDoctorInfo = dutyDoctorInfos.stream()
                                    .filter(dutyDoctorInfo -> dutyDoctorInfo.getRemainAvailableNumber() > 0
                                            && dutyDoctorInfo.getDutySourceId() == finalDutySourceId).findFirst();
                            if (optionalDutyDoctorInfo.isPresent()) {
                                doctorId = optionalDutyDoctorInfo.get().getDoctorId();
                                break;
                            }
                        }
                    }
                    // 未找到医生号源信息
                    if (doctorId == null) {
                        // 不能刷新太快，否则会被拒绝服务：403
                        Thread.sleep(1000);
                        continue;
                    }

                    // 获取短信验证码
                    sendBookSmsCode();

                    System.out.println("输入手机上收到的【114预约挂号】的短信验证码：");
                    String smsVerifyCode = scanner.nextLine();

                    String personalId = getPersonalId(hospitalBookInfo, doctorId, String.valueOf(dutySourceId));

                    String result = bookConfirm(hospitalBookInfo, doctorId, String.valueOf(dutySourceId), personalId, smsVerifyCode);
                    System.out.println(result);

                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.get("code").equals(1)) {
                        // 发通知邮件、发短信
                    }
                    return;
                }
            } catch (Exception e) {
                logger.error("抢票异常", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询指定医院科室、指定日期是否有号
     *
     * @param hospitalBookInfo 预约信息
     * @return 是否有足够的号源
     */
    private boolean hasNumber(HospitalBookInfo hospitalBookInfo) {
        String getCalendarIdURL = "http://www.114yygh.com/dpt/week/calendar.htm";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Origin", "http://www.114yygh.com");
        httpHeaders.add("Accept-Encoding", "gzip, deflate");
        httpHeaders.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("Accept", "application/json, text/javascript, */*; q=0.01");
        httpHeaders.add("Referer", "http://www.114yygh.com/dpt/calendar/" + hospitalBookInfo.getHospitalId()
                + "-" + hospitalBookInfo.getDepartmentId() + ".htm");
        httpHeaders.add("X-Requested-With", "XMLHttpRequest");
        httpHeaders.add("Connection", "keep-alive");

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("hospitalId", hospitalBookInfo.getHospitalId());
        parameter.add("departmentId", hospitalBookInfo.getDepartmentId());
        parameter.add("departmentName", "");
        parameter.add("isAjax", "true");
        parameter.add("relType", "0");
        parameter.add("sdFirstId", "0");
        parameter.add("sdSecondId", "0");
        parameter.set("week", "1");
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        // 这里如果请求太快，会被拒绝：Response 403 FORBIDDEN
        String responseBody = restTemplate.postForObject(getCalendarIdURL, request, String.class);
        HospitalCalendarDTO hospitalCalendarDTO = JSON.parseObject(responseBody, HospitalCalendarDTO.class);

        Date firstDutyDate = hospitalCalendarDTO.getDutyCalendars().get(0).getDutyDate();
        if (hospitalBookInfo.getBookDate().before(firstDutyDate)) {
            return false;
        }

        LocalDateTime bookDateTime = LocalDateTime.ofInstant(hospitalBookInfo.getBookDate().toInstant(), ZoneId.systemDefault());
        LocalDateTime firstDutyDateTime = LocalDateTime.ofInstant(firstDutyDate.toInstant(), ZoneId.systemDefault());
        long days = DAYS.between(firstDutyDateTime, bookDateTime);
        long skipWeek = days / 7;
        if (skipWeek > 0) {
            parameter.set("week", String.valueOf(skipWeek + 1));
            responseBody = restTemplate.postForObject(getCalendarIdURL, request, String.class);
            hospitalCalendarDTO = JSON.parseObject(responseBody, HospitalCalendarDTO.class);
        }

        if (hospitalCalendarDTO.isHasNumber()) {
            for (DutyCalendar dutyCalendar : hospitalCalendarDTO.getDutyCalendars()) {
                if (dutyCalendar.getDutyDate().equals(hospitalBookInfo.getBookDate())) {
                    return dutyCalendar.getRemainAvailableNumber() > 0;
                }
            }
        }
        return false;
    }

    /**
     * 获取值班信息
     *
     * @param hospitalBookInfo 预约信息
     * @return 值班信息
     */
    private DutyDTO getDoctorIdAndDutySourceId(HospitalBookInfo hospitalBookInfo) throws NoSuchAlgorithmException {
        String dutyURL = "http://www.114yygh.com/dpt/build/duty.htm";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Origin", "http://www.114yygh.com");
        httpHeaders.add("Accept-Encoding", "gzip, deflate");
        httpHeaders.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("Accept", "application/json, text/javascript, */*;q=0.01");
        httpHeaders.add("Referer", "http://www.114yygh.com/dpt/calendar/.htm");
        httpHeaders.add("X-Requested-With", "XMLHttpRequest");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("Cookie", cookies);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("hospitalId", hospitalBookInfo.getHospitalId());
        parameter.add("departmentId", hospitalBookInfo.getDepartmentId());
        // 预约日期
        parameter.add("dutyDate", dateFormat.format(hospitalBookInfo.getBookDate()));
        parameter.add("isAjax", "true");
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        String responseBody = restTemplate.postForObject(dutyURL, request, String.class);
        // {"data":[],"hasError":true,"code":2009,"msg":"用户未登录!"}
        // {"data":[],"hasError":true,"code":4023,"msg":"不在放号周期内"}
        // {"code":1,"msg":"成功","data":{"1":[{"hospitalId":162,"departmentId":200002273,"doctorId":201133438,"dutySourceId":63476880,"portrait":"","doctorName":"普通口腔种植科","doctorTitleName":"","skill":"口腔种植技术，活动义齿及固定义齿修复，全口义齿修复。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133440,"dutySourceId":63476884,"portrait":"","doctorName":"普通口腔正畸","doctorTitleName":"","skill":"矫正牙齿。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133442,"dutySourceId":63476888,"portrait":"","doctorName":"普通口腔牙周粘膜","doctorTitleName":"","skill":"开展龈下刮治术，牙周手术、膜瓣手术、牙冠延长术等多种牙周粘膜疾病的治疗。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133444,"dutySourceId":63476892,"portrait":"","doctorName":"普通口腔修复","doctorTitleName":"","skill":"镶牙与修复。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133446,"dutySourceId":63476896,"portrait":"","doctorName":"普通口腔外科","doctorTitleName":"","skill":"拔牙、颌面部畸形、颞下颌关节以及呼吸睡眠暂停的治疗。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133448,"dutySourceId":63476900,"portrait":"","doctorName":"普通口腔内科","doctorTitleName":"","skill":"口腔内科,补牙,牙体牙髓病、根尖周病的诊治，牙齿美白术。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133460,"dutySourceId":63476905,"portrait":"","doctorName":"普通儿童牙科","doctorTitleName":"","skill":"小儿牙科疾病的诊治。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133170,"dutySourceId":63476860,"portrait":"","doctorName":"张彤","doctorTitleName":"副主任医师","skill":"正畸，只看口腔正畸科、儿童及成人各类错颌畸形的诊断、设计及正畸治疗。牙科美容治疗。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133196,"dutySourceId":63476863,"portrait":"","doctorName":"杨瑟飞","doctorTitleName":"副主任医师","skill":"种植，只看口腔种植科、前牙美容修复、精密附着体、全口义齿修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133212,"dutySourceId":63476865,"portrait":"","doctorName":"徐璐璐","doctorTitleName":"副主任医师","skill":"正畸，只看口腔正畸科、青年及成人的各类错牙合畸形矫治，骨性错牙合的早期生长改良治疗；舌侧隐形正畸治疗","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133244,"dutySourceId":63476867,"portrait":"","doctorName":"汪林","doctorTitleName":"副主任医师","skill":"老年口腔科，只看老年各种复杂根管治疗，显微根尖外科手术，牙体保存、微创美学修复，牙缺失种植修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133270,"dutySourceId":63476869,"portrait":"","doctorName":"刘一涵","doctorTitleName":"副主任医师","skill":"种植，只看口腔种植科，种植牙、种植美学修复、前牙美容修复及活动及固定义齿修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133288,"dutySourceId":63476871,"portrait":"","doctorName":"李锐","doctorTitleName":"副主任医师","skill":"正畸，只看口腔正畸科、青少年错颌畸形矫正治疗","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133354,"dutySourceId":63476877,"portrait":"","doctorName":"邓斌","doctorTitleName":"副主任医师","skill":"种植，只看口腔修复科、前牙个性化仿真美学修复，种植牙修复，种植牙及牙体缺损计算机辅助设计制作即刻修复","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133362,"dutySourceId":63476879,"portrait":"","doctorName":"常平","doctorTitleName":"副主任医师","skill":"儿童牙科、只看儿童牙病的诊治。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133940,"dutySourceId":63476912,"portrait":"","doctorName":"陈鹏","doctorTitleName":"副主任医师","skill":"口外（不拔牙），口腔颌面颈部肿瘤、颌面创伤骨折、颌面整形美容，正颌外科","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201135408,"dutySourceId":63476917,"portrait":"","doctorName":"王俊成","doctorTitleName":"副主任医师","skill":"种植、只看口腔种植科各类牙列缺损的人工种植牙技术。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201147192,"dutySourceId":63476919,"portrait":"","doctorName":"章斌","doctorTitleName":"副主任医师","skill":"口腔正畸，擅长青少年及成人复杂错合畸形矫正，无托槽隐形矫治、舌侧隐形矫治及正畸正颌外科。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133072,"dutySourceId":63476858,"portrait":"","doctorName":"徐娟","doctorTitleName":"主任医师","skill":"正畸，只看口腔正畸科、各类错颌畸形的矫治，正颌手术的术前术后正畸，颞下颌紊乱综合征的辅助正畸治疗。","totalFee":80.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201149106,"dutySourceId":63476921,"portrait":"","doctorName":"布静秋","doctorTitleName":"主任医师","skill":"口外，只看口腔外科、头颈部肿瘤的外科治疗，颜面整形美容外科（不拔牙）","totalFee":80.0,"remainAvailableNumber":1,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133600,"dutySourceId":63476908,"portrait":"","doctorName":"温伟生","doctorTitleName":"主任医师","skill":"口外，只看口腔外科、采用自体组织移植、骨代用品植入及牵张成骨技术修复颌面部组织缺损,（不拔牙）","totalFee":100.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133648,"dutySourceId":63476910,"portrait":"","doctorName":"刘荣森","doctorTitleName":"主任医师","skill":"牙周，只看牙周粘膜科，牙周前牙美观治疗修复，种植修复，牙周再生修复治疗，口腔粘膜病诊治","totalFee":100.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201134128,"dutySourceId":63476916,"portrait":"","doctorName":"郭斌","doctorTitleName":"主任医师","skill":"口内，只看口腔内科， 牙髓牙周联合病损的治疗、微创/无创前牙美容修复、冷光美白牙、数字化导航种植修复","totalFee":100.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"1","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":1,"dutyCodeName":"上午","periodDutySources":null}],"2":[{"hospitalId":162,"departmentId":200002273,"doctorId":201133438,"dutySourceId":63476882,"portrait":"","doctorName":"普通口腔种植科","doctorTitleName":"","skill":"口腔种植技术，活动义齿及固定义齿修复，全口义齿修复。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133440,"dutySourceId":63476886,"portrait":"","doctorName":"普通口腔正畸","doctorTitleName":"","skill":"矫正牙齿。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133442,"dutySourceId":63476890,"portrait":"","doctorName":"普通口腔牙周粘膜","doctorTitleName":"","skill":"开展龈下刮治术，牙周手术、膜瓣手术、牙冠延长术等多种牙周粘膜疾病的治疗。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133444,"dutySourceId":63476894,"portrait":"","doctorName":"普通口腔修复","doctorTitleName":"","skill":"镶牙与修复。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133446,"dutySourceId":63476898,"portrait":"","doctorName":"普通口腔外科","doctorTitleName":"","skill":"拔牙、颌面部畸形、颞下颌关节以及呼吸睡眠暂停的治疗。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133448,"dutySourceId":63476902,"portrait":"","doctorName":"普通口腔内科","doctorTitleName":"","skill":"口腔内科,补牙,牙体牙髓病、根尖周病的诊治，牙齿美白术。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133450,"dutySourceId":63476904,"portrait":"","doctorName":"普通口腔洁治科","doctorTitleName":"","skill":"口腔洁治科,只洗牙、洗牙前请提前一周先化验血常规、传染病、凝血等化验","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133460,"dutySourceId":63476906,"portrait":"","doctorName":"普通儿童牙科","doctorTitleName":"","skill":"小儿牙科疾病的诊治。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133474,"dutySourceId":63476907,"portrait":"","doctorName":"老年口腔病科","doctorTitleName":"","skill":"只看老年口腔疾病。","totalFee":50.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133168,"dutySourceId":63476859,"portrait":"","doctorName":"张贤华","doctorTitleName":"副主任医师","skill":"牙周粘膜科、只看牙周病的基础治疗、牙周组织再生治疗、牙冠延长手术以及口腔黏膜病的中西医系统治疗。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133172,"dutySourceId":63476861,"portrait":"","doctorName":"张蕾","doctorTitleName":"副主任医师","skill":"口外，只看口腔外科、颌面颈部肿瘤及综合靶向，唇腭裂整复口腔颌面颈部血管及淋巴管瘤的治疗（不拔牙）","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133196,"dutySourceId":63476864,"portrait":"","doctorName":"杨瑟飞","doctorTitleName":"副主任医师","skill":"种植，只看口腔种植科、前牙美容修复、精密附着体、全口义齿修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133214,"dutySourceId":63476866,"portrait":"","doctorName":"肖瑞","doctorTitleName":"副主任医师","skill":"牙周粘膜科、只看中老年牙周病、牙周组织美学修复、口腔黏膜病的诊疗。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133244,"dutySourceId":63476868,"portrait":"","doctorName":"汪林","doctorTitleName":"副主任医师","skill":"老年口腔科，只看老年各种复杂根管治疗，显微根尖外科手术，牙体保存、微创美学修复，牙缺失种植修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133270,"dutySourceId":63476870,"portrait":"","doctorName":"刘一涵","doctorTitleName":"副主任医师","skill":"种植，只看口腔种植科，种植牙、种植美学修复、前牙美容修复及活动及固定义齿修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133288,"dutySourceId":63476873,"portrait":"","doctorName":"李锐","doctorTitleName":"副主任医师","skill":"正畸，只看口腔正畸科、青少年错颌畸形矫正治疗","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133306,"dutySourceId":63476875,"portrait":"","doctorName":"姜华","doctorTitleName":"副主任医师","skill":"修复，只看口腔修复科、前牙美学修复、种植义齿修复及各类固定及活动义齿修复。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":2,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133332,"dutySourceId":63476876,"portrait":"","doctorName":"顾斌","doctorTitleName":"副主任医师","skill":"修复，只看口腔修复科、前牙美学修复、牙体缺损牙列缺损与缺失的修复，颞下颌关节病的治疗及口腔种植治疗。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133354,"dutySourceId":63476878,"portrait":"","doctorName":"邓斌","doctorTitleName":"副主任医师","skill":"种植，只看口腔修复科、前牙个性化仿真美学修复，种植牙修复，种植牙及牙体缺损计算机辅助设计制作即刻修复","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133940,"dutySourceId":63476914,"portrait":"","doctorName":"陈鹏","doctorTitleName":"副主任医师","skill":"口外（不拔牙），口腔颌面颈部肿瘤、颌面创伤骨折、颌面整形美容，正颌外科","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201135408,"dutySourceId":63476918,"portrait":"","doctorName":"王俊成","doctorTitleName":"副主任医师","skill":"种植、只看口腔种植科各类牙列缺损的人工种植牙技术。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201147192,"dutySourceId":63476920,"portrait":"","doctorName":"章斌","doctorTitleName":"副主任医师","skill":"口腔正畸，擅长青少年及成人复杂错合畸形矫正，无托槽隐形矫治、舌侧隐形矫治及正畸正颌外科。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null},{"hospitalId":162,"departmentId":200002273,"doctorId":201133648,"dutySourceId":63476911,"portrait":"","doctorName":"刘荣森","doctorTitleName":"主任医师","skill":"牙周，只看牙周粘膜科，牙周前牙美观治疗修复，种植修复，牙周再生修复治疗，口腔粘膜病诊治","totalFee":100.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"2","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":2,"dutyCodeName":"下午","periodDutySources":null}],"4":[{"hospitalId":162,"departmentId":200002273,"doctorId":201133214,"dutySourceId":63590911,"portrait":"","doctorName":"肖瑞","doctorTitleName":"副主任医师","skill":"牙周粘膜科、只看中老年牙周病、牙周组织美学修复、口腔黏膜病的诊疗。","totalFee":60.0,"remainAvailableNumber":0,"dutyStatus":1,"drCode":null,"deptCode":null,"planCode":null,"dutyDate":null,"dutyCode":"4","dutyType":null,"mapDoctorId":null,"departmentName":null,"isShowFee":1,"dutyCodeIndex":4,"dutyCodeName":"晚上","periodDutySources":null}]},"hasError":false}
        // System.out.println(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 原网页中的js代码
        // success: function (response) {
        // if (response.hasError) {
        //     if (response.code == 2009) {
        //         goonLogin();
        //         window.location.href = url;
        //     } else {
        //         alert(response.msg);
        //         return;
        //     }
        // }
        // ……

        if (jsonObject.getBoolean("hasError")) {
            System.out.println(responseBody);
            int code = (int) jsonObject.get("code");
            // 未登录
            if (code == NO_LOGIN) {
                cookies = platformLogin(hospitalBookInfo);
                System.out.println(cookies);
            } else {
                // 其他未知的错误返回信息？怎么办
                // 不知道干什么的话，那就继续吧
            }
            return null;
        }

        return JSON.parseObject(responseBody, DutyDTO.class);
    }

    /**
     * 登陆获取cookie
     *
     * @param hospitalBookInfo 预约信息
     * @return cookies
     */
    private String platformLogin(HospitalBookInfo hospitalBookInfo) throws NoSuchAlgorithmException {
        String loginStep1URL = "http://www.114yygh.com/account/loginStep1.htm";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("Cache-Control", "max-age=0");
        httpHeaders.add("Origin", "http://www.114yygh.com");
        httpHeaders.add("Upgrade-Insecure-Requests", "1");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        httpHeaders.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        httpHeaders.add("Referer", "http://www.114yygh.com/account/login.htm");
        httpHeaders.add("Accept-Encoding", "gzip, deflate");
        httpHeaders.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("mobileNo", hospitalBookInfo.getMobileNo());
        parameter.add("redirectUrl", "/index.htm");
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        String responseBody = restTemplate.postForObject(loginStep1URL, request, String.class);


        // 解析网页form表单中的参数
        Document document = Jsoup.parse(responseBody);
        String token = document.select("#loginStep2_pwd_form > input[name=token]").attr("value");
        String mobileNoForm = document.select("#loginStep2_pwd_form > input[name=mobileNo]").attr("value");
        String smsType = document.select("#loginStep2_pwd_form > input[name=smsType]").attr("value");
        String loginType = document.select("#loginStep2_pwd_form > input[name=loginType]").attr("value");
        String redirectUrl = document.select("#loginStep2_pwd_form > input[name=redirectUrl]").attr("value");
        // 明文密码进行base64编码
        // String password = new String(Base64.getEncoder().encode(hospitalBookInfo.getPassword().getBytes()));
        // 新加密方式改为了SHA1
        String password = sha1Hex(hospitalBookInfo.getPassword());

        MultiValueMap<String, String> parameter2 = new LinkedMultiValueMap<>();
        parameter2.add("token", token);
        parameter2.add("mobileNo", mobileNoForm);
        parameter2.add("smsType", smsType);
        parameter2.add("loginType", loginType);
        parameter2.add("redirectUrl", redirectUrl);
        parameter2.add("password", password);
        HttpEntity<Object> request2 = new HttpEntity<>(parameter2, httpHeaders);

        String loginStep2URL = "http://www.114yygh.com/account/loginStep2.htm";

        ResponseEntity<String> response2 = restTemplate.postForEntity(loginStep2URL, request2, String.class);
        return String.join(";", response2.getHeaders().get("Set-Cookie"));
    }

    // sha1加密
    private String sha1Hex(String password) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        byte[] digest = sha.digest(password.getBytes());

        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            int val = ((int) digest[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * 发送验证码，预定的最后一步
     */
    private void sendBookSmsCode() {
        String sendSmsCodeURL = "http://www.114yygh.com/v/sendorder.htm";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Origin", "http://www.114yygh.com");
        httpHeaders.add("Cookie", cookies);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("isAjax", "true");
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        String responseBody = restTemplate.postForObject(sendSmsCodeURL, request, String.class);
        System.out.println(responseBody);
        // {"code":200,"msg":"OK."}
    }

    // 获取就诊人id
    private String getPersonalId(HospitalBookInfo hospitalBookInfo, String doctorId, String dutySourceId) throws IOException {
        String confirmURL = "http://www.114yygh.com/order/confirm/{0}-{1}-{2}-{3}.htm";
        confirmURL = MessageFormat.format(confirmURL, hospitalBookInfo.getHospitalId(), hospitalBookInfo.getDepartmentId(), doctorId, dutySourceId);

        Document doc = Jsoup.connect(confirmURL)
                .header("Cookie", cookies)
                .get();
        Elements persons = doc.select("#Reservation_info > div.Rese_db > dl > div.personnel[name]");
        for (Element person : persons) {
            String personName = person.select("div.infoRight > span.name").get(0).text();
            if (personName.equals(hospitalBookInfo.getName())) {
                return person.attr("name");
            }
        }
        return null;
    }

    // 确认预约
    private String bookConfirm(HospitalBookInfo hospitalBookInfo, String doctorId, String dutySourceId,
                               String personalId, String smsVerifyCode) {
        String confirmURL = "http://www.114yygh.com/order/confirmV1.htm";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Origin", "http://www.114yygh.com");
        httpHeaders.add("Accept-Encoding", "gzip, deflate");
        httpHeaders.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("Accept", "application/json, text/javascript, */*;q=0.01");
        httpHeaders.add("Referer", "http://www.114yygh.com/order/confirm/" + hospitalBookInfo.getHospitalId() + "-" + hospitalBookInfo.getDepartmentId() +
                "-" + doctorId + "-" + dutySourceId + ".htm");
        httpHeaders.add("X-Requested-With", "XMLHttpRequest");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("Cookie", cookies);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("hospitalId", hospitalBookInfo.getHospitalId());
        parameter.add("departmentId", hospitalBookInfo.getDepartmentId());
        parameter.add("doctorId", doctorId);
        parameter.add("dutySourceId", dutySourceId);
        parameter.add("patientId", personalId);
        parameter.add("smsVerifyCode", smsVerifyCode);
        parameter.add("kinshipName", hospitalBookInfo.getName());
        parameter.add("isAjax", "true");
        HttpEntity<Object> request = new HttpEntity<>(parameter, httpHeaders);

        return restTemplate.postForObject(confirmURL, request, String.class);
    }


    // 取消预约
    // curl 'http://www.114yygh.com/order/cel.htm' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://www.114yygh.com/u/index.htm?basePath=%2F&tokenUrl=http%3A%2F%2Fupload.idc3%2Fxora%2Ftoken%2F2.htm&uploadUrl=http%3A%2F%2Fupload.idc3%2Fxora%2Fupload%2Fs.htm&imageTypes=image%2Fpng%2C+image%2Fgif%2C+.jpeg%2C+.jpg&v=1558151970376&specializedHospitalUrl=http%3A%2F%2Fzmyygh.114menhu.com%2F&hps=http%3A%2F%2Fimg.114yygh.com%2F&hs=http%3A%2F%2Fimg.114yygh.com%2Fws%2F1.0%2Fhs%2F&mobileNo=13006317071&smsType=13&yzm=&redirectUrl=http%3A%2F%2Fwww.114yygh.com%2Fu%2Findex.htm&userSize=1' -H 'Origin: http://www.114yygh.com' -H 'X-Requested-With: XMLHttpRequest' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' --data 'orderId=105908387&hospitalType=1&isAjax=true' --compressed

}
