package biz.impl;

import biz.CatalogBiz;
import dao.CatalogDao;
import entity.Catalog;
import global.DaoFactory;

import java.util.List;

public class CatalogBizImpl implements CatalogBiz {
    private CatalogDao catalogDao= DaoFactory.getInstance().getDao(CatalogDao.class);

    public void add(List<Catalog> list) {
        catalogDao.batchInsert(list);
    }

    public void remove(int id) {
        catalogDao.delete(id);
    }

    public Catalog getRoot() {
        return catalogDao.select(10000);
    }
}
