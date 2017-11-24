import com.cui.code.rpc.dubbo.facade.OrderFacade;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * dubbo测试
 * Created by cuishixiang on 2017-11-23.
 */
public class DubboTest {

    @Test
    public void testDubbo() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring/dubbo-provider.xml"});
        context.start();
        System.in.read(); // press any key to exit


        ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext("classpath:/spring/dubbo-consumer.xml");
        context2.start();
        OrderFacade orderFacade = (OrderFacade) context2.getBean("orderFacade"); // obtain proxy object for remote invocation
        String orderNo = orderFacade.save("firstOrder"); // execute remote invocation
        System.out.println(orderNo); // show the result

    }
}
