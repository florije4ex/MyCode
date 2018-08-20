package com.cui.spring.test;

import org.springframework.util.StringUtils;

/**
 * Created by cuishixiang on 2017-10-31.
 */
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public FXNewsProvider() {
    }

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
