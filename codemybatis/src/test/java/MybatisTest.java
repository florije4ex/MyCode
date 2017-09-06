import com.cui.mybatis.dataobject.OrdUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * mybatis原始开发测试
 * Created by cuishixiang on 2017-09-05.
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void beforeMethod() throws IOException {
        String resource = "config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 单个查询
     */
    @Test
    public void testMybatiSelectOne() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object object = sqlSession.selectOne("queryUserById", 2560);
        System.out.println(object);
        OrdUser ordUser = (OrdUser) object;
        System.out.println(ordUser.getUsername() + ordUser.getMobile());
        sqlSession.close();
    }

    /**
     * 多个查询
     */
    @Test
    public void testMybatisSelectList() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> list = sqlSession.selectList("queryUserByUsername", "%13258%");
        System.out.println(list.size());
        System.out.println(list);
        sqlSession.close();
    }

    /**
     * 保存用户，没有返回id
     */
    @Test
    public void testMybatisSaveUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang");
        ordUser.setMobile("15600036028");
        ordUser.setOpenId("123456789");
        int rows = sqlSession.insert("saveUser", ordUser);
        System.out.println(rows);
        System.out.println(ordUser);

        // 需要进行事务提交才生效
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 保存用户并返回自增主键id
     */
    @Test
    public void testMybatisSaveUserReturnKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang2");
        ordUser.setMobile("15600036028");
        ordUser.setOpenId("123456789");
        int rows = sqlSession.insert("saveUserReturnKey", ordUser);
        System.out.println(rows);
        System.out.println(ordUser);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 更新用户
     */
    @Test
    public void testMybatisUpdateUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang2");
        ordUser.setMobile("15600036029");
        ordUser.setOpenId("123456780");
        int rows = sqlSession.update("updateUser", ordUser);
        System.out.println(rows);
        System.out.println(ordUser);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 删除用户
     */
    @Test
    public void testMybatisDeleteUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int rows = sqlSession.delete("deleteUserById", 125341);
        System.out.println(rows);

        sqlSession.commit();
        sqlSession.close();
    }
}
