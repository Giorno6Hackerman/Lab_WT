package app.model.dao.interfaces;

import java.util.List;

import app.entities.Book;
import app.entities.Record;
import app.model.dao.exception.DAOException;

public interface RecordDAO {
    void addRecord(Record record) throws DAOException;
    void deleteRecord(Record record) throws DAOException;
    void deleteRecordsByBook(Book book) throws DAOException;
    void updateRecord(Record record) throws DAOException;
    List<Record> getRecords() throws DAOException;
}
