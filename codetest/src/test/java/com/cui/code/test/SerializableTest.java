package com.cui.code.test;

import com.cui.code.test.model.EvilUser;
import org.junit.Test;

import java.io.*;

/**
 * 序列化测试
 *
 * @author cuiswing
 * @date 2019-07-16
 */
public class SerializableTest {

    /**
     * 用java自带的ObjectOutputStream 序列化对象到文件
     */
    @Test
    public void testSerializable() {
        EvilUser evilUser = new EvilUser();
        evilUser.setAge(18);
        evilUser.setName("张三");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("evilUser.bin"))) {
            outputStream.writeObject(evilUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过java的 ObjectInputStream 反序列化是安全的，是直接从文件反序列化到内存的，并没有重新创建对象
     */
    @Test
    public void testDeserializable() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("evilUser.bin"))) {
            EvilUser evilUser = (EvilUser) inputStream.readObject();
            System.out.println(evilUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
