package com.cui.code.test;

/**
 * @author cuishixiang
 * @date 2019-02-27
 */
public class Contact {
    private String type;
    private int number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "type='" + type + '\'' +
                ", number=" + number +
                '}';
    }
}
