package app.model.dao.pool;

import app.model.dao.exception.DAOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static final LinkedList<Connection> connections =new LinkedList<Connection>();
    private static final int size = 4;

    private ConnectionPool() {}

    public static ConnectionPool getInstance() throws DAOException {
        if(instance == null)
        {
            try{
                InitialContext initialContext = new InitialContext();
                Context envContext = (Context) initialContext.lookup("java:/comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/library");

                for (int i = 0; i < size; i++) {
                    connections.add(ds.getConnection());
                }

                instance = new ConnectionPool();
            } catch (SQLException | NamingException ex) {
                throw new DAOException(ex);
            }
        }
        return instance;
    }

    public Connection getConnection() {
        while(connections.size() == 0) {
            Thread.yield();
        }

        Connection connection = connections.stream().findFirst().orElseThrow();
        connections.remove(connection);
        return connection;
    }

    public void addConnection(Connection connection) {
        if(!connections.contains(connection)) {
            connections.add(connection);
        }
    }
}
