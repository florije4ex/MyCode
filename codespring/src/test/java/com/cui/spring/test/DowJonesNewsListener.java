package com.cui.spring.test;

/**
 * Created by cuishixiang on 2017-10-31.
 */
public class DowJonesNewsListener implements IFXNewsListener {

    @Override
    public String[] getAvailableNewsIds() {
        return new String[]{"1", "2"};
    }
}
