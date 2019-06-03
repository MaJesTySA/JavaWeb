package biz.impl;

import biz.CakeBiz;
import dao.CakeDao;
import entity.Cake;
import global.DaoFactory;

import java.util.List;

public class CakeBizImpl implements CakeBiz {
    private CakeDao cakeDao= DaoFactory.getInstance().getDao(CakeDao.class);
    public void add(Cake cake){
        cakeDao.insert(cake);
    }
    public void edit(Cake cake){
        cakeDao.update(cake);
    }
    public void remove(int id){
        cakeDao.delete(id);
    }
    public Cake get(int id){
        return cakeDao.select(id);
    }
    public List<Cake> getAll(){
        return cakeDao.selectAll();
    }
    //特卖
    public Cake getSpecial() {
        List<Cake> list=cakeDao.selectByStatus("特卖");
        if(list.size()>0)
            return list.get(0);
        return null;
    }
    //推荐
    public List<Cake> getForIndex() {
        return cakeDao.selectByStatus("推荐");
    }

    public List<Cake> getForCatalog(int cid) {
        return cakeDao.selectByCid(cid);
    }
}
