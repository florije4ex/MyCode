package com.cui.code.test;

import com.cui.code.test.model.Customer;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * yaml解析测试
 *
 * @author cuishixiang
 * @date 2019-02-27
 */
public class YamlTest {

    @Test
    public void testParse() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/customer.yaml");
        Map<String, Object> map = yaml.load(inputStream);
        System.out.println(map);
    }

    @Test
    public void testParseObject() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/customer.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(customer);
    }

    @Test
    public void testParseNestedObject() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/customer_with_contact_details_and_address.yaml");
        Customer customer = yaml.load(inputStream);

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());

        assertEquals("mobile", customer.getContactDetails()
                .get(0)
                .getType());
        assertEquals(123456789, customer.getContactDetails()
                .get(0)
                .getNumber());
        assertEquals("landline", customer.getContactDetails()
                .get(1)
                .getType());
        assertEquals(456786868, customer.getContactDetails()
                .get(1)
                .getNumber());
        assertNotNull(customer.getHomeAddress());
        assertEquals("Xyz, DEF Street", customer.getHomeAddress()
                .getLine());
    }

    /**
     * 解析单文件中的多个yaml文档
     */
    @Test
    public void testLoadingMultipleDocuments() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/customers.yaml");

        int count = 0;
        for (Object object : yaml.loadAll(inputStream)) {
            count++;
            assertTrue(object instanceof Customer);
        }
        assertEquals(2, count);
    }

}
