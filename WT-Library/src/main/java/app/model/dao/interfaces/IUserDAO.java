package app.model.dao.interfaces;

import app.entities.User;

public interface IUserDAO {
    void registerUser(User user);
    void loginUser(User user);
    User getUserById(int id);
}
