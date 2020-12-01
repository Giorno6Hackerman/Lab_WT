package app.model.dao.interfaces;

import app.entities.User;

public interface IUserDAO {
    void updateUser(final long userId, final User user);

    void registerUser(final User user);

    User getUserById(final User user);
}
