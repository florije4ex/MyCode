package com.cui.spring.test;

import org.springframework.stereotype.Component;

/**
 * Created by cuishixiang on 2017-10-31.
 */
@Component
public class DowJonesNewsListener implements IFXNewsListener {

    @Override
    public String[] getAvailableNewsIds() {
        return new String[]{"1", "2"};
    }
}
