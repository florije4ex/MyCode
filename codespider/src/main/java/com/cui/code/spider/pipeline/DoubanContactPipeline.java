package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanContactDAO;
import com.cui.code.spider.dal.dataobject.DoubanContactDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 豆瓣联系人数据处理
 *
 * @author CUI
 * @date 2020-01-12
 */
@Slf4j
public class DoubanContactPipeline implements Pipeline {

    /**
     * 处理逻辑：
     * 全新的数据：新增保存
     * 更新的数据：
     * 1、已关注的人不再关注后，需要修改状态并备注；
     * 2、不再关注后，又重新关注了，修改状态并备注
     * 3、新增的关注人，需要添加；
     * 4、已关注未变化的人不变，已关注的人昵称修改了也需要同步并备注
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        String fromId = resultItems.get("fromId");
        String fromName = resultItems.get("fromName");
        List<DoubanContactDO> toList = resultItems.get("toList");
        if (toList.isEmpty()) {
            log.info("fromId:{},fromName:{},没有关注任何人", fromId, fromName);
            return;
        }

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanContactDAO doubanContactDAO = sqlSession.getMapper(DoubanContactDAO.class);
        DoubanContactDO firstContact = doubanContactDAO.getFirstContact(fromId, 1);
        // 全部新增
        if (firstContact == null) {
            int rows = doubanContactDAO.saveContactBatch(fromId, fromName, 1, toList);
            log.info("contact from:{},{}, save rows:{}", fromId, fromName, rows);
            sqlSession.commit();
            sqlSession.close();
            return;
        }
        // 更新数据处理
        List<DoubanContactDO> oldContactDOList = doubanContactDAO.listContactByType(fromId, 1);
        Set<String> oldToIdSet = oldContactDOList.stream().map(DoubanContactDO::getToId).collect(Collectors.toSet());
        Set<String> newToIdSet = toList.stream().map(DoubanContactDO::getToId).collect(Collectors.toSet());

        // 新增的关注：
        List<DoubanContactDO> newContactDOList = toList.stream()
                .filter(newContact -> !oldToIdSet.contains(newContact.getToId())).collect(Collectors.toList());
        if (!newContactDOList.isEmpty()) {
            int rows = doubanContactDAO.saveContactBatch(fromId, fromName, 1, newContactDOList);
            log.info("contact from:{},{}, 新增关注 {} 人", fromId, fromName, rows);
        }

        // 取消关注：
        List<String> removeToIdList = oldContactDOList.stream()
                .filter(oldContact -> oldContact.getDeleted() == 0 && !newToIdSet.contains(oldContact.getToId()))
                .map(DoubanContactDO::getToId).collect(Collectors.toList());
        if (!removeToIdList.isEmpty()) {
            int rows = doubanContactDAO.updateByToIdList(fromId, fromName, removeToIdList, 1, "取关");
            log.info("contact from:{},{}, 取关 {} 人", fromId, fromName, rows);
        }

        // 取关后重新关注的：
        List<String> againToIdList = oldContactDOList.stream()
                .filter(oldContact -> oldContact.getDeleted() == 1 && newToIdSet.contains(oldContact.getToId()))
                .map(DoubanContactDO::getToId).collect(Collectors.toList());
        if (!againToIdList.isEmpty()) {
            int rows = doubanContactDAO.updateByToIdList(fromId, fromName, againToIdList, 0, "重关");
            log.info("contact from:{},{}, 重关 {} 人", fromId, fromName, rows);
        }

        // 其他更新：
        List<String> updateToIdList = oldContactDOList.stream()
                .filter(oldContact -> oldContact.getDeleted() == 0 && newToIdSet.contains(oldContact.getToId()))
                .map(DoubanContactDO::getToId).collect(Collectors.toList());
        if (!updateToIdList.isEmpty()) {
            int rows = doubanContactDAO.updateByToIdList(fromId, fromName, updateToIdList, 0, "更新");
            log.info("contact from:{},{}, 更新 {} 人", fromId, fromName, rows);
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
