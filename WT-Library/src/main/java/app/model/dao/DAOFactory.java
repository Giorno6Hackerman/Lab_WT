package app.model.dao;

import app.model.dao.interfaces.BookDAO;
import app.model.dao.interfaces.RecordDAO;
import app.model.dao.interfaces.UserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private DAOFactory() {};
    private final BookDAO bookDAO = new BookDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final RecordDAO recordDAO = new RecordDAOImpl();

    public static DAOFactory getInstance() { return instance;}
    public BookDAO getBookDAO() { return bookDAO;}
    public UserDAO getUserDAO() { return userDAO;}
    public RecordDAO getRecordDAO() { return recordDAO;}
}
