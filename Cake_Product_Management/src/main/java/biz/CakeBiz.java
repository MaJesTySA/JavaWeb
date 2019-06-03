package biz;

import entity.Cake;

import java.util.List;

public interface CakeBiz {
    void add(Cake cake);
    void edit(Cake cake);
    void remove(int id);
    Cake get(int id);
    List<Cake> getAll();
    Cake getSpecial();
    //特卖
    List<Cake> getForIndex();
    List<Cake> getForCatalog(int cid);
}
