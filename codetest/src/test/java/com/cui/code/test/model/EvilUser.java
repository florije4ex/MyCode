package com.cui.code.test.model;

import lombok.Data;

import java.io.IOException;
import java.io.Serializable;

/**
 * 恶意构造的侵入对象的class，通过注入到服务器获取服务器的权限
 *
 * @author cuiswing
 * @date 2019-07-16
 */
@Data
public class EvilUser implements Serializable {

    private String name;
    private int age;

    public EvilUser() {
        init();
    }

    public void init() {
        try {
            System.out.println("启动chrome：");
            // 执行命令：友好启动Chrome，您可以想象一个黑客会执行什么命令,shell反弹
            Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
