package global;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private SqlSessionFactory sessionFactory;
    private DaoFactory(){
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        try {
            sessionFactory=sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("/mybatis.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DaoFactory getInstance(){
        if(daoFactory==null){
            daoFactory=new DaoFactory();
        }
        return daoFactory;
    }

    public <T> T getDao(Class<T> tClass){
        //设置自动提交(commit)，默认false。
        return sessionFactory.openSession(true).getMapper(tClass);
    }
}
