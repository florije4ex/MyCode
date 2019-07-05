package com.cui.code.test.copy;

import com.cui.code.test.model.Customer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 各家 属性拷贝工具 性能测试
 * <p>
 * 1、Apache BeanUtils.copyProperties()
 * 2、spring framework的BeanUtils
 * 3、cglib的BeanCopier
 * 4、Apache BeanUtils包的PropertyUtils类
 * 5、自己手写的getter+setter
 *
 * @author cuiswing
 * @date 2019-07-05
 */
@RunWith(Parameterized.class)
public class PropertiesCopierTest {

    @Parameterized.Parameter(0)
    public PropertiesCopier propertiesCopier;

    // 测试次数
    private static List<Integer> testTimes = Arrays.asList(1, 100, 1000, 10_000, 100_000, 1_000_000);

    // 测试结果以 markdown 表格的形式输出
    private static StringBuilder resultBuilder = new StringBuilder("|实现工具|执行1次耗时（ms）|100|1,000|10,000|100,000|1,000,000|\n")
            .append("|----|----|----|----|----|----|----|\n");


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new StaticCglibBeanCopierPropertiesCopier()});
        params.add(new Object[]{new CglibBeanCopierPropertiesCopier()});
        params.add(new Object[]{new SpringBeanUtilsPropertiesCopier()});
        params.add(new Object[]{new ApachePropertyUtilsPropertiesCopier()});
        params.add(new Object[]{new ApacheBeanUtilsPropertiesCopier()});
        params.add(new Object[]{new GetterSetterPropertiesCopier()});
        return params;
    }

    @Before
    public void setUp() {
        String name = propertiesCopier.getClass().getSimpleName().replace("PropertiesCopier", "");
        resultBuilder.append("|").append(name).append("|");
    }


    @Test
    public void copyProperties() throws Exception {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("yaml/customer_with_contact_details_and_address.yaml");
        Customer source = yaml.load(inputStream);
        Customer target = new Customer();

        // 预热一次
        // propertiesCopier.copyProperties(source, target);

        for (Integer time : testTimes) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < time; i++) {
                propertiesCopier.copyProperties(source, target);
            }
            resultBuilder.append(System.currentTimeMillis() - start).append("|");
        }
        resultBuilder.append("\n");
    }


    @AfterClass
    public static void tearDown() {
        System.out.println("测试结果：");
        System.out.println(resultBuilder);
    }
}
