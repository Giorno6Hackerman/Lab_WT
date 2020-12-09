package app.services;

import app.entities.Book;
import app.entities.Record;
import app.entities.User;
import app.model.dao.DAOFactory;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.BookDAO;
import app.model.dao.interfaces.RecordDAO;
import app.services.exception.ServiceException;
import app.services.interfaces.LibraryService;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {
    private final BookDAO bookDao = DAOFactory.getInstance().getBookDAO();
    private final RecordDAO recordDao = DAOFactory.getInstance().getRecordDAO();

    @Override
    public boolean addNewBook(Book book) throws ServiceException {
        if (book.getName().equals("") || book.getAuthor().equals("") || book.getGenre().equals("")
                || book.getCount() < 0) {
            return false;
        } else {
            try {
                bookDao.addBook(book);
                return true;
            } catch (DAOException ex) {
                throw new ServiceException(ex);
            }
        }
    }

    @Override
    public boolean addEditedBook(Book book) throws ServiceException {
        if (book.getName().equals("") || book.getAuthor().equals("") || book.getGenre().equals("")
                || book.getCount() < 0 || book.getId() < 0) {
            return false;
        } else {
            try {
                bookDao.editBook(book);
                return true;
            } catch (DAOException ex) {
                throw new ServiceException(ex);
            }
        }
    }

    @Override
    public void deleteBook(Book book) throws ServiceException {
        try {
            bookDao.deleteBook(book);
            recordDao.deleteRecordsByBook(book);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Book> getBookList() throws ServiceException {
        try {
            return bookDao.getBooks();
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Book getBookById(String id) throws ServiceException {
        try {
            Book book = bookDao.getBookById(Integer.parseInt(id));
            if (book.getId() == -1) {
                throw new ServiceException("No such book there");
            } else {
                return book;
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void addNewRecord(Record record) throws ServiceException {
        try {
            recordDao.addRecord(record);
            Book book = record.getBook();
            book.setCount(book.getCount() - 1);
            bookDao.editBook(book);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Record> getRecords() throws ServiceException {
        try {
            return recordDao.getRecords();
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Record> getUserRecords(User user) throws ServiceException {
        try {
            return recordDao.getUserRecords(user);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }


    @Override
    public void deleteRecord(Record record) throws ServiceException {
        try {
            recordDao.deleteRecord(record);
            Book book = record.getBook();
            book.setCount(book.getCount() + 1);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void updateRecord(Record record) throws ServiceException {
        try {
            recordDao.updateRecord(record);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }
}
