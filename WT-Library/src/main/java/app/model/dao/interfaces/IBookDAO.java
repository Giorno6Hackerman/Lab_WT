package app.model.dao.interfaces;

import app.entities.Book;
import app.model.dao.exception.DAOException;

import java.util.List;

public interface IBookDAO {
    void addBook(Book book) throws DAOException;
    void deleteBook(Book book) throws DAOException;
    void editBook(Book book) throws DAOException;
    Book getBookById(int id) throws DAOException;
    List<Book> getBooks() throws DAOException;
}
