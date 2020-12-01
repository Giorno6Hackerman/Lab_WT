package app.model.dao;


import app.entities.User;
import app.model.dao.interfaces.IUserDAO;

public class UserDAO implements IUserDAO {
    @Override
    public void updateUser(final long userId, final User user){

    }

    @Override
    public void registerUser(final User user){

    }

    @Override
    public User getUserById(final User user){
        return null;
    }
}
