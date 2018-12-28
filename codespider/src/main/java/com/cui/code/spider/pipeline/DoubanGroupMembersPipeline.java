package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanGroupDAO;
import com.cui.code.spider.dal.dao.DoubanGroupMemberDAO;
import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import com.cui.code.spider.dal.dataobject.DoubanGroupMemberDO;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2018-12-27
 */
public class DoubanGroupMembersPipeline implements Pipeline {
    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        String groupCode = resultItems.get("groupCode");
        if (groupCode != null && !groupCode.isEmpty()) {
            String ownerId = resultItems.get("ownerId");
            String ownerName = resultItems.get("ownerName");
            DoubanGroupDAO doubanGroupDAO = sqlSession.getMapper(DoubanGroupDAO.class);
            DoubanGroupDO doubanGroupDO = new DoubanGroupDO();
            doubanGroupDO.setCode(groupCode);
            doubanGroupDO.setOwnerId(ownerId);
            doubanGroupDO.setOwnerName(ownerName);
            doubanGroupDAO.updateOwner(doubanGroupDO);
        }

        List<DoubanGroupMemberDO> memberDOList = resultItems.get("doubanGroupMemberDOList");
        DoubanGroupMemberDAO doubanGroupMemberDAO = sqlSession.getMapper(DoubanGroupMemberDAO.class);
        int rows = doubanGroupMemberDAO.saveBatch(memberDOList);
        System.out.println(rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
