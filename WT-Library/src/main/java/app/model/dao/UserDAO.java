package app.model.dao;


import app.entities.User;
import app.model.dao.interfaces.IUserDAO;

import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public void registerUser(final User user){

    }

    @Override
    public void loginUser(User user) {

    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
