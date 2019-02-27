package com.cui.code.net.util;

import com.cui.code.net.model.BookCardInfo;
import com.cui.code.net.model.SubscribeIdEnum;
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
    private static final String CONFIG_FILE = "/config/user.yml";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static BookCardInfo getBookCardInfo() {
        Yaml yaml = new Yaml(new Constructor(BookCardInfo.class));
        InputStream inputStream = YamlUtil.class
                .getResourceAsStream(CONFIG_FILE);
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


}
