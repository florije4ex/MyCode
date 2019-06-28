package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.HospitalDBConfig;
import com.cui.code.spider.dal.dao.HospitalDAO;
import com.cui.code.spider.dal.dataobject.HospitalDO;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 114挂号网医院数据处理
 *
 * @author cuiswing
 * @date 2019-06-28
 */
public class HospitalList114Pipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<HospitalDO> hospitalDOList = resultItems.get("hospitalDOList");

        SqlSession sqlSession = HospitalDBConfig.sqlSessionFactory.openSession();
        HospitalDAO hospitalDAO = sqlSession.getMapper(HospitalDAO.class);
        int rows = hospitalDAO.saveBatch(hospitalDOList);
        System.out.println(rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
