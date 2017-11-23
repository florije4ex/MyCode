package biz.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by 世祥 on 2017/5/7.
 */
@Component("student")
@Controller("student")
@Service
@Repository
@Scope("prototype")
public class StudentVO {

    public StudentVO() {
        System.out.println("StudentVO无参构造函数");
    }
}
