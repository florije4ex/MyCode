package com.cui.spring.test.config;

import lombok.Data;

/**
 * test
 *
 * @author cuiswing
 * @date 2019-06-16
 */
@Data
public class DataSource {

    private String url;

    public DataSource(String url) {
        this.url = url;
    }
}
