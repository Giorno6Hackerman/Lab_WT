package app.model.dao.exception;

public class DAOException extends Exception{
    public DAOException() {
        super();
    }

    public DAOException(Exception ex) {
        super(ex);
    }

    public DAOException(String message, Exception ex) {
        super(message, ex);
    }

    public DAOException(String message) {
        super(message);
    }
}
