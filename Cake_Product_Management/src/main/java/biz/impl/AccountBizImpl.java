package biz.impl;

import biz.AccountBiz;
import dao.AccountDao;
import entity.Account;
import global.DaoFactory;
import java.util.List;

public class AccountBizImpl implements AccountBiz {
    private AccountDao accountDao= DaoFactory.getInstance().getDao(AccountDao.class);
    public Account login(String name, String password) {
        List<Account> list = accountDao.selectByName(name);
        for(Account account:list)
            if(account.getPassword().equals(password))
                return account;
        return null;
    }
}
