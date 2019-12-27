package com.cui.code.spider.pipeline.lynk;

import com.cui.code.spider.dal.config.LynkDBConfig;
import com.cui.code.spider.dal.dao.lynk.BookInfoDAO;
import com.cui.code.spider.dal.dataobject.lynk.BookInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 预约信息数据处理
 *
 * @author CUI
 * @date 2019-12-27
 */
@Slf4j
public class BookInfoPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<BookInfoDO> bookInfoDOList = resultItems.get("bookInfoDOList");
        if (bookInfoDOList.isEmpty()) {
            log.warn("没有任何数据");
            return;
        }

        SqlSession sqlSession = LynkDBConfig.sqlSessionFactory.openSession();
        BookInfoDAO bookInfoDAO = sqlSession.getMapper(BookInfoDAO.class);
        int rows = bookInfoDAO.saveBatchBookInfo(bookInfoDOList);
        System.out.println(rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
