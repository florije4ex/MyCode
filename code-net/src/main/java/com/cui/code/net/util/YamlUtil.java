package com.cui.code.net.util;

import com.cui.code.net.model.BookCardInfo;
import com.cui.code.net.model.SubscribeIdEnum;
import com.cui.code.net.model.hospital.HospitalBookInfo;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * yaml文件解析
 *
 * @author cuishixiang
 * @date 2019-02-27
 */
public class YamlUtil {
    private static final String LY_CONFIG_FILE = "/config/user.yml";
    private static final String HOSPITAL_CONFIG_FILE = "/config/hospitalBook.yml";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static BookCardInfo getBookCardInfo() {
        Yaml yaml = new Yaml(new Constructor(BookCardInfo.class));
        InputStream inputStream = YamlUtil.class.getResourceAsStream(LY_CONFIG_FILE);
        BookCardInfo bookCardInfo = yaml.load(inputStream);

        LocalDateTime startTime = LocalDateTime.parse(bookCardInfo.getTimingStartTimeConfig(), dateTimeFormatter);
        Instant instant = startTime.atZone(ZoneId.systemDefault()).toInstant();
        bookCardInfo.setTimingStartTime(Date.from(instant));

        LocalDateTime endTime = LocalDateTime.parse(bookCardInfo.getEndTimeConfig(), dateTimeFormatter);
        Instant endInstant = endTime.atZone(ZoneId.systemDefault()).toInstant();
        bookCardInfo.setEndTime(Date.from(endInstant));

        SubscribeIdEnum subscribeIdEnum = SubscribeIdEnum.valueOf(bookCardInfo.getSubscribeName());
        bookCardInfo.setSubscribeId(subscribeIdEnum.getSubscribeId());

        return bookCardInfo;
    }

    /**
     * 根据配置文件获取预约挂号信息
     *
     * @return 预约信息
     */
    public static HospitalBookInfo getHospitalBookInfo() {
        Yaml yaml = new Yaml(new Constructor(HospitalBookInfo.class));
        InputStream inputStream = YamlUtil.class.getResourceAsStream(HOSPITAL_CONFIG_FILE);
        HospitalBookInfo hospitalBookInfo = yaml.load(inputStream);

        return hospitalBookInfo;
    }

}
