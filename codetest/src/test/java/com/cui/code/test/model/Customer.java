package com.cui.code.test.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2019-02-27
 */
@Data
@Builder
public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private List<Contact> contactDetails;
    private Address homeAddress;


    // lombok @Data和@Builder一起使用时 无法添加无参构造方法，需要加这个@Tolerate
    // 添加函数或者构造方法，让lombok假装它不存在（不感知）。
    @Tolerate
    public Customer() {

    }
}
