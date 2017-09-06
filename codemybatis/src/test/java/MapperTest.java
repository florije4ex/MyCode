import com.cui.mybatis.daointerface.OrdUserMapper;
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
 * Mapper动态代理测试
 * Created by cuishixiang on 2017-09-06.
 */
public class MapperTest {
    SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void init() throws IOException {
        String resource = "config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testSelectOne() {
        // 获取sqlSession，和spring整合后由spring管理
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 从sqlSession中获取Mapper接口的代理对象
        OrdUserMapper ordUserMapper = sqlSession.getMapper(OrdUserMapper.class);
        // 执行查询方法
        OrdUser ordUser = ordUserMapper.queryUserById(2555);
        System.out.println(ordUser);

        // 和spring整合后由spring管理
        sqlSession.close();
    }

    @Test
    public void testSelectList() {
        // 获取sqlSession，和spring整合后由spring管理
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 从sqlSession中获取Mapper接口的代理对象
        OrdUserMapper ordUserMapper = sqlSession.getMapper(OrdUserMapper.class);
        // 执行查询方法
        List<OrdUser> ordUserList = ordUserMapper.queryUserByUsername("%13258%");
        System.out.println(ordUserList);

        // 和spring整合后由spring管理
        sqlSession.close();
    }

    @Test
    public void testSave() {
        // 获取sqlSession，和spring整合后由spring管理
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 从sqlSession中获取Mapper接口的代理对象
        OrdUserMapper ordUserMapper = sqlSession.getMapper(OrdUserMapper.class);
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang2");
        ordUser.setMobile("15600036028");
        ordUser.setOpenId("123456789");
        // 执行查询方法
        int rows = ordUserMapper.saveUser(ordUser);
        System.out.println(rows);
        System.out.println(ordUser);

        sqlSession.commit();
        // 和spring整合后由spring管理
        sqlSession.close();
    }

    @Test
    public void testUpdate() {
        // 获取sqlSession，和spring整合后由spring管理
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 从sqlSession中获取Mapper接口的代理对象
        OrdUserMapper ordUserMapper = sqlSession.getMapper(OrdUserMapper.class);
        OrdUser ordUser = new OrdUser();
        ordUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
        ordUser.setUsername("cuishixiang2");
        ordUser.setMobile("15600036021");
        ordUser.setOpenId("123456781");
        // 执行查询方法
        int rows = ordUserMapper.updateUser(ordUser);
        System.out.println(rows);
        System.out.println(ordUser);

        sqlSession.commit();
        // 和spring整合后由spring管理
        sqlSession.close();
    }

    @Test
    public void testDelete() {
        // 获取sqlSession，和spring整合后由spring管理
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 从sqlSession中获取Mapper接口的代理对象
        OrdUserMapper ordUserMapper = sqlSession.getMapper(OrdUserMapper.class);
        // 执行查询方法
        int rows = ordUserMapper.deleteUserById(125343);
        System.out.println(rows);

        sqlSession.commit();
        // 和spring整合后由spring管理
        sqlSession.close();
    }
}
