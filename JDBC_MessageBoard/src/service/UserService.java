package service;

import bean.User;
import dao.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean saveUser(String username, String password,String realName,String address,String phone){
        User user=new User(username,password,realName,phone,address);
        return userDAO.insertUser(user);
    }

    public boolean checkUsername(String username){
        return userDAO.checkUsername(username);
    }
}
