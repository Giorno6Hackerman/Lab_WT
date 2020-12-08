package app.model.dao.interfaces;

import app.entities.User;
import app.model.dao.exception.DAOException;

import java.util.List;

public interface IUserDAO {
    int registerUser(User user) throws DAOException;
    int loginUser(User user) throws DAOException;
    User getUserById(int id) throws DAOException;
    List<User> getUsers() throws DAOException;
}
