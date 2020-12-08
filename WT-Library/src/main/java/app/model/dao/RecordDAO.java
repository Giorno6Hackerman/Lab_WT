package app.model.dao;

import app.entities.Record;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IRecordDAO;

import java.util.List;

public class RecordDAO implements IRecordDAO {
    @Override
    public void addRecord(Record record) throws DAOException {

    }

    @Override
    public void deleteRecord(Record record) throws DAOException {

    }

    @Override
    public void deleteRecordsByBook(Record record) throws DAOException {

    }

    @Override
    public void updateRecord(Record record) throws DAOException {

    }

    @Override
    public List<Record> getRecords() throws DAOException {
        return null;
    }
}
