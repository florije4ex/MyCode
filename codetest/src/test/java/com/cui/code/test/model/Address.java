package com.cui.code.test.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @author cuishixiang
 * @date 2019-02-27
 */
@Data
@Builder
public class Address {
    private String line;
    private String city;
    private String state;
    private Integer zip;

    @Tolerate
    public Address() {

    }
}
