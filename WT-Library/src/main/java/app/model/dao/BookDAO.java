package app.model.dao;

import app.entities.Book;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IBookDAO;
import app.model.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookDAO implements IBookDAO {
    @Override
    public void addBook(Book book) throws DAOException {
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var addBookRequest = "INSERT INTO books (name, author, genre, count) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(addBookRequest);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getCount());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
    }

    @Override
    public void deleteBook(Book book) throws DAOException {

    }

    @Override
    public void editBook(Book book) throws DAOException {

    }

    @Override
    public Book getBookById(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Book> getBooks() throws DAOException {
        return null;
    }
}
