package app.model.dao.interfaces;

import app.entities.User;
import app.model.dao.exception.DAOException;

import java.util.List;

public interface IUserDAO {
    void registerUser(User user) throws DAOException;
    void loginUser(User user) throws DAOException;
    User getUserById(int id) throws DAOException;
    List<User> getUsers() throws DAOException;
}
