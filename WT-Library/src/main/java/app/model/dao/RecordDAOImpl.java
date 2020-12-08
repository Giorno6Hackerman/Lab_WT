package app.model.dao;

import app.entities.Book;
import app.entities.OrderType;
import app.entities.Record;
import app.entities.User;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.RecordDAO;
import app.model.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDAOImpl implements RecordDAO {
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
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var deleteRecordRequest = "DELETE FROM records WHERE user_id=? AND book_id=?";
            PreparedStatement ps = connection.prepareStatement(deleteRecordRequest);
            ps.setInt(1, record.getUser().getId());
            ps.setInt(2, record.getBook().getId());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
    }

    @Override
    public void deleteRecordsByBook(Book book) throws DAOException {
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var deleteRecordRequest = "DELETE FROM records WHERE book_id=?";
            PreparedStatement ps = connection.prepareStatement(deleteRecordRequest);
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
    }

    @Override
    public void updateRecord(Record record) throws DAOException {
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var editRecordRequest = "UPDATE records SET type=? WHERE user_id=? AND book_id=?";
            PreparedStatement ps = connection.prepareStatement(editRecordRequest);
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
    public List<Record> getRecords() throws DAOException {
        List<Record> records = new ArrayList<>();
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var getRecordsRequest = "SELECT * FROM records";
            PreparedStatement ps = connection.prepareStatement(getRecordsRequest);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Record record = new Record();
                User user = DAOFactory.getInstance().getUserDAO().getUserById(rs.getInt("user_id"));
                Book book = DAOFactory.getInstance().getBookDAO().getBookById(rs.getInt("book_id"));;
                record.setType(OrderType.values()[rs.getInt("type")]);
                record.setUser(user);
                record.setBook(book);
                records.add(record);
            }
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
        return records;
    }
}
