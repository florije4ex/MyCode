package biz.vo;

/**
 * Created by 世祥 on 2017/5/7.
 */
public class VOFactory {

    public static UserVO createUserVO() {
        System.out.println("静态工厂创建对象");
        return new UserVO();
    }


    public StudentVO createStudent(){
        System.out.println("实例工厂创建对象");
        return new StudentVO();
    }
}
