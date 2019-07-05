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
public class Contact {
    private String type;
    private int number;

    @Tolerate
    public Contact() {

    }
}
