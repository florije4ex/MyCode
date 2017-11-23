package biz.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by 世祥 on 2017/5/7.
 */
public class UserVO {
    private String name;
    private Integer age;

    @Autowired
    private StudentVO studentVO;

    public UserVO() {
        System.out.println("UserVO无参构造器");
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    @Value("18")
    public void setAge(Integer age) {
        this.age = age;
    }

    @PostConstruct
    public void init() {
        System.out.println("创建对象后");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁对象前");
    }
}
