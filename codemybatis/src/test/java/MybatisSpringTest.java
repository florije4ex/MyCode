import com.cui.mybatis.daointerface.OrdUserMapper;
import com.cui.mybatis.dataobject.OrdUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * mybatis-Spring注入测试代码
 * Created by cuishixiang on 2017-09-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisSpringTest {

    @Resource
    private OrdUserMapper ordUserMapper;

    @Test
    public void testQuery() {
        OrdUser ordUser = ordUserMapper.queryUserById(2456);
        System.out.println(ordUser);
    }


    /**
     * 保存用户并返回自增主键id
     */
    @Test
    public void testSave() {
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang2");
        ordUser.setMobile("15600036028");
        ordUser.setOpenId("123456789");
        int rows = ordUserMapper.saveUser(ordUser);

        System.out.println(rows);
        System.out.println(ordUser);
    }


    /**
     * 删除用户
     */
    @Test
    @Transactional
    public void testDelete() {
        int rows = ordUserMapper.deleteUserById(125346);
        System.out.println(rows);
        int i = 1 / 0;
    }

}
