package app.model.dao;

import app.entities.Book;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IBookDAO;

import java.util.List;

public class BookDAO implements IBookDAO {
    @Override
    public void addBook(Book book) throws DAOException {

    }

    @Override
    public void deleteBook(Book book) throws DAOException {

    }

    @Override
    public void editBook(Book book) throws DAOException {

    }

    @Override
    public Book getBookById(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Book> getBooks() throws DAOException {
        return null;
    }
}
