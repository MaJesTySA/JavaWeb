package dao;

import entity.Users;
import org.apache.ibatis.session.SqlSession;
import utils.SqlSessionFactoryUtils;
import java.util.List;

public class UsersDAO {
    private SqlSession sqlSession;
    private Users user;
    private List<Users> list;
    
    private SqlSession getSession(){
        sqlSession=
                SqlSessionFactoryUtils.
                        getSqlSessionFactory().
                        openSession();
        return sqlSession;
    }
    
    public List<Users> findAll(){
        try {
            list=getSession().selectList("findUsers");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return list;
    }
    
    public Users findById(Integer id){
        try {
            user=getSession().selectOne("findUsers",new Users(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return user;
    }
    
    public Users addUser(Users user){
        try{
            getSession().insert("addUser",user);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return user;
    }
    
    public Users updateUser(Users user){
        try{
            getSession().update("updateUser",user);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return user;
    }
    
    public void delUser(Integer id){
        try{
            getSession().delete("delUser",id);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
