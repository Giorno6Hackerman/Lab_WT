package app.model.dao.interfaces;

import java.util.List;
import app.entities.Record;
import app.model.dao.exception.DAOException;

public interface IRecordDAO {
    void addRecord(Record record) throws DAOException;
    void deleteRecord(Record record) throws DAOException;
    void deleteRecordsByBook(Record record) throws DAOException;
    void updateRecord(Record record) throws DAOException;
    List<Record> getRecords() throws DAOException;
}
