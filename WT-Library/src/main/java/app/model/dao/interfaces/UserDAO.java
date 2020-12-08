package app.model.dao.interfaces;

import app.entities.User;
import app.model.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {
    User registerUser(User user) throws DAOException;
    User loginUser(User user) throws DAOException;
    User getUserById(int id) throws DAOException;
    User getUserByLogin(String login) throws DAOException;
    List<User> getUsers() throws DAOException;
}
