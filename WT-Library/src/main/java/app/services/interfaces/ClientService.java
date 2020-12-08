package app.services.interfaces;

import app.entities.User;
import app.services.exception.ServiceException;

public interface ClientService {
    User register(User user) throws ServiceException;
    User login(User user) throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
}
