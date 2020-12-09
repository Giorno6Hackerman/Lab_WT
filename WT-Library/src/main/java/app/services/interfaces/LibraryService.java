package app.services.interfaces;

import app.entities.Book;
import app.entities.User;
import app.entities.Record;
import app.services.exception.ServiceException;

import java.util.List;

public interface LibraryService {
    boolean addNewBook(Book book) throws ServiceException;
    boolean addEditedBook(Book book) throws ServiceException;
    void deleteBook(Book book) throws ServiceException;
    List<Book> getBookList() throws ServiceException;
    Book getBookById(String id) throws ServiceException;
    void addNewRecord(Record record) throws ServiceException;
    List<Record> getRecords() throws ServiceException;
    List<Record> getUserRecords(User user) throws ServiceException;
    void deleteRecord(Record record) throws ServiceException;
    void updateRecord(Record record) throws ServiceException;
}
