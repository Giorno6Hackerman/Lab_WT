package app.services;

import app.entities.User;
import app.model.dao.DAOFactory;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.UserDAO;
import app.services.exception.ServiceException;
import app.services.interfaces.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final UserDAO userDao = DAOFactory.getInstance().getUserDAO();

    @Override
    public User register(User user) throws ServiceException {
        if (user.getLogin().equals("") || user.getPasswordHash().equals("") || user.getName().equals("") ||
                user.getSurname().equals("")) {
            throw new ServiceException("These fields can't be empty");
        } else {
            try {
                if (!isLoginExists(user)) {
                    return userDao.registerUser(user);
                } else {
                    return null;
                }
            } catch (DAOException ex) {
                throw new ServiceException(ex);
            }
        }
    }

    @Override
    public User login(User user) throws ServiceException {
        if (user.getLogin().equals("")) {
            throw new ServiceException("Login field can't be empty");
        } else {
            try {
                if (isPasswordCorrect(user)) {
                    return userDao.loginUser(user);
                } else {
                    return null;
                }
            } catch (DAOException ex) {
                throw new ServiceException(ex);
            }
        }
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            if (user.getId() == -1) {
                throw new ServiceException("No such user there");
            } else {
                return user;
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private boolean isLoginExists(User user) throws ServiceException {
        try {
            List<User> users = userDao.getUsers();
            return users.stream().anyMatch(u -> u.getLogin().equals(user.getLogin()));
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private boolean isPasswordCorrect(User user) throws ServiceException {
        try {
            List<User> users = userDao.getUsers();
            User comparedUser = users.stream()
                    .filter(u -> u.getLogin().equals(user.getLogin()))
                    .findFirst().orElse(null);
            return (comparedUser == null) ? false : comparedUser.getPasswordHash().equals(user.getPasswordHash());
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }
}
