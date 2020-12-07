package app.model.dao.interfaces;

import app.entities.Book;

import java.util.List;

public interface IBookDAO {
    void addBook(Book book);
    void deleteBook(Book book);
    void editBook(Book book);
    Book getBookById(int id);
    List<Book> getBooks();
}
