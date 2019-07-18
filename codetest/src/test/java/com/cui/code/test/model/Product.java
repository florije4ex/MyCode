package com.cui.code.test.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cuiswing
 * @date 2019-07-18
 */
@Data
public class Product implements Serializable {
    private String code;
    private String name;
    private double price;
    private String description;
}
