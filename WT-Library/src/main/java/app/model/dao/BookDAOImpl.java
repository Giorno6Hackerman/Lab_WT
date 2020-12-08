package app.model.dao;

import app.entities.Book;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.BookDAO;
import app.model.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
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
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var deleteBookRequest = "DELETE FROM books WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(deleteBookRequest);
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
    }

    @Override
    public void editBook(Book book) throws DAOException {
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var editBookRequest = "UPDATE books SET name=?, author=?, genre=?, count=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(editBookRequest);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getCount());
            ps.setInt(5, book.getId());
            ps.executeUpdate();
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
    }

    @Override
    public Book getBookById(int id) throws DAOException {
        Book book = new Book();
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var getBookByIdRequest = "SELECT * FROM books WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(getBookByIdRequest);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setCount(rs.getInt("count"));
            }
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }

        return book;
    }

    @Override
    public List<Book> getBooks() throws DAOException {
        List<Book> books = new ArrayList<>();
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var getBooksRequest = "SELECT * FROM books";
            PreparedStatement ps = connection.prepareStatement(getBooksRequest);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setCount(rs.getInt("count"));
                books.add(book);
            }
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
        return books;
    }
}
