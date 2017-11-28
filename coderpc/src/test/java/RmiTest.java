import com.cui.code.rmi.dto.Order;
import com.cui.code.rmi.provider.OrderService;
import org.junit.Test;

import java.rmi.Naming;
import java.util.Date;

/**
 * RMI测试
 * Created by cuishixiang on 2017-11-28.
 */
public class RmiTest {

    /**
     * rmi调用测试
     *
     * @throws Exception
     */
    @Test
    public void testRmi() throws Exception {
        String url = "rmi://localhost:1099/com.cui.code.rmi.provider.impl.OrderServiceImpl";
        OrderService helloService = (OrderService) Naming.lookup(url);
        Order order = new Order();
        order.setOrderNo("001");
        order.setCreateDate(new Date());
        Order result = helloService.save(order);
        System.out.println(result);
    }

}
