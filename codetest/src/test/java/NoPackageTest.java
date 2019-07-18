import java.io.IOException;

/**
 * 无 package 包名测试类
 *
 * @author cuiswing
 * @date 2019-07-18
 */
public class NoPackageTest {

    public NoPackageTest() {
        init();
    }

    public void init() {
        try {
            System.out.println("启动chrome：");
            // 执行命令：友好启动Chrome，您可以想象一个黑客会执行什么命令,shell反弹
            Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome"});
        } catch (IOException e) {
            // ignore
        }
    }
}
