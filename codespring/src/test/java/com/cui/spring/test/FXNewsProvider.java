package com.cui.spring.test;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by cuishixiang on 2017-10-31.
 */
@Component
public class FXNewsProvider {
    @Resource
    private IFXNewsListener newsListener;
    @Resource
    private IFXNewsPersister newPersistener;

    //通过setter方式注入时必须添加此构造函数，因为set是在构造出对象之后才发生的，如果没有此函数的话，Java没有默认给此class添加默认构造函数，就会挂了
    public FXNewsProvider() {
    }

    //构造注入方式必须添加此构造函数
    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersister newPersistener) {
        this.newsListener = newsListener;
        this.newPersistener = newPersistener;
    }

    public String getAndPersistNews() {
        String[] newsIds = newsListener.getAvailableNewsIds();
//        if (ArrayUtils.isEmpty(newsIds)) {
//            return;
//        }
//
//        for (String newsId : newsIds) {
//            FXNewsBean newsBean = newsListener.getNewsByPK(newsId);
//            newPersistener.persistNews(newsBean);
//            newsListener.postProcessIfNecessary(newsId);
//        }
        return StringUtils.arrayToCommaDelimitedString(newsIds);
    }

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewPersistener() {
        return newPersistener;
    }

    public void setNewPersistener(IFXNewsPersister newPersistener) {
        this.newPersistener = newPersistener;
    }
}
