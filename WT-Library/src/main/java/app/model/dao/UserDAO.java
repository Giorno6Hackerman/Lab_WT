package app.model.dao;


import app.entities.User;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IUserDAO;

import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public void registerUser(final User user) throws DAOException  {

    }

    @Override
    public void loginUser(User user) throws DAOException {

    }

    @Override
    public User getUserById(int id) throws DAOException {
        return null;
    }

    @Override
    public List<User> getUsers() throws DAOException {
        return null;
    }
}
