import com.cui.code.rpc.facade.RpcImporter;
import com.cui.code.rpc.provider.RpcExporter;
import com.cui.code.rpc.service.UserService;
import com.cui.code.rpc.service.impl.UserServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by cuishixiang on 2017-08-21.
 */
public class RpcTest {

    @Test
    public void testRpc() throws InterruptedException {

        //启动rpc服务方
        new Thread(() -> {
            try {
                RpcExporter.exporter("localhost", 8088);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(100);

        //调用rpc服务
        RpcImporter<UserService> importer = new RpcImporter<>();
        UserService userService = importer.importer(UserServiceImpl.class, new InetSocketAddress("localhost", 8088));
        String result = userService.save("张三");
        System.out.println(result);


    }
}
