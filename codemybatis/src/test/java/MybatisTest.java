import com.cui.mybatis.dataobject.OrdUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

    @Test
    public void testMybatis() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object object = sqlSession.selectOne("queryUserById", 2560);
        System.out.println(object);
        OrdUser ordUser = (OrdUser) object;
        System.out.println(ordUser.getUsername() + ordUser.getMobile());
        sqlSession.close();
    }
}
