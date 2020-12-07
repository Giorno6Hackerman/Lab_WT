package app.entities;

import java.io.Serializable;
import java.util.Objects;

public class Record implements Serializable {
    private OrderType type;
    private User user;
    private Book book;

    public OrderType getType(){
        return type;
    }

    public void setType(OrderType type){
        this.type = type;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Book getBook(){
        return book;
    }

    public void setBook(Book book){
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Record))
            return false;
        Record record = (Record) o;
        return type == record.type && user.equals(record.user) && book.equals(record.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, user, book);
    }

    @Override
    public String toString() {
        return "Record{type=" + type + ", user='" + user + "', book='" + book + '}';
    }
}
