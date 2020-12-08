package app.model.dao;


import app.entities.Role;
import app.entities.User;
import app.model.dao.exception.DAOException;
import app.model.dao.interfaces.IUserDAO;
import app.model.dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public int registerUser(final User user) throws DAOException  {
        Connection connection = null;
        int id = -1;
        try{
            connection = ConnectionPool.getInstance().getConnection();

            var registerUserRequest = "INSERT INTO users (name, surname, login, passwordHash, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(registerUserRequest, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPasswordHash());
            ps.setInt(5, user.getRole().ordinal());
            ps.executeUpdate();

            var keys = ps.getGeneratedKeys();
            if (keys.next()) {
                id = keys.getInt(1);
                user.setId(id);
            }
        } catch(SQLException ex) {
            throw new DAOException("User registration failed", ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }

        return id;
    }

    @Override
    public int loginUser(User user) throws DAOException {
        Connection connection = null;
        int id = -1;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var loginUserRequest = "SELECT * FROM users WHERE login=? AND passwordHash=?";
            PreparedStatement ps = connection.prepareStatement(loginUserRequest);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPasswordHash());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setRole(Role.values()[rs.getInt("role")]);
            }
        } catch(SQLException ex) {
            throw new DAOException("User login failed", ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
        return id;
    }

    @Override
    public User getUserById(int id) throws DAOException {
        User user = new User();
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var getUserByIdRequest = "SELECT * FROM users WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(getUserByIdRequest);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setLogin(rs.getString("login"));
                user.setPasswordHash(rs.getString("passwordHash"));
                user.setRole(Role.values()[rs.getInt("role")]);
            }
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> getUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();

            var getUsersRequest = "SELECT * FROM users";
            PreparedStatement ps = connection.prepareStatement(getUsersRequest);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setLogin(rs.getString("login"));
                user.setPasswordHash(rs.getString("passwordHash"));
                user.setRole(Role.values()[rs.getInt("role")]);
                users.add(user);
            }
        } catch(SQLException ex) {
            throw new DAOException(ex);
        } finally {
            ConnectionPool.getInstance().addConnection(connection);
        }
        return users;
    }
}
