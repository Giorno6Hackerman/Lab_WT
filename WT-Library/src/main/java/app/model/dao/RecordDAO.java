package app.model.dao;

import app.entities.Record;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IRecordDAO;
import app.model.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RecordDAO implements IRecordDAO {
    @Override
    public void addRecord(Record record) throws DAOException {
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var addRecordRequest = "INSERT INTO records (type, user_id, book_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(addRecordRequest);
            ps.setInt(1, record.getType().ordinal());
            ps.setInt(2, record.getUser().getId());
            ps.setInt(3, record.getBook().getId());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
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
