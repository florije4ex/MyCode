import com.cui.code.rmi.dto.Order;
import com.cui.code.rmi.provider.OrderService;
import com.cui.code.zkrmi.ServiceConsumer;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * RMI加上zookeeper模拟高可用的服务
 * 根据以下步骤验证 RMI 服务的高可用性：
 * <p>
 * 运行两个 Server 程序，一定要确保 port 是不同的。
 * 运行一个 Client 程序。
 * 停止其中一个 Server 程序，并观察 Client 控制台的变化（停止一个 Server 不会导致 Client 端调用失败）。
 * 重新启动刚才关闭的 Server 程序，继续观察 Client 控制台变化（新启动的 Server 会加入候选）。
 * 先后停止所有的 Server 程序，还是观察 Client 控制台变化（Client 会重试连接，多次连接失败后，自动关闭）。
 * <p>
 * 参考：http://www.importnew.com/20344.html
 * <p>
 * Created by cuishixiang on 2017-11-28.
 */
public class ZkRMITest {

    /**
     * 使用一个“死循环”来模拟每隔 3 秒钟调用一次远程方法。
     *
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Test
    public void testZkRMI() throws RemoteException, InterruptedException {

        ServiceConsumer consumer = new ServiceConsumer();
        while (true) {
            OrderService orderService = consumer.lookup();
            Order order = new Order();
            order.setOrderNo("001");
            order.setCreateDate(new Date());
            Order result = orderService.save(order);
            System.out.println(result);
            Thread.sleep(3000);
        }
    }
}
