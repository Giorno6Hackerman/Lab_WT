package app.model.dao;

import app.model.dao.interfaces.IBookDAO;
import app.model.dao.interfaces.IRecordDAO;
import app.model.dao.interfaces.IUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private DAOFactory() {};
    private final IBookDAO bookDAO = new BookDAO();
    private final IUserDAO userDAO = new UserDAO();
    private final IRecordDAO recordDAO = new RecordDAO();

    public DAOFactory getInstance() { return instance;}
    public IBookDAO getBookDAO() { return bookDAO;}
    public IUserDAO getUserDAO() { return userDAO;}
    public IRecordDAO getRecordDAO() { return recordDAO;}
}
