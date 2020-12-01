package app.model.dao.interfaces;

import app.entities.Book;

public interface IBookDAO {
    void addBook(Book book);
    void deleteBook(Book book);
}
