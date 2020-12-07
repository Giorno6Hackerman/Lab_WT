package app.entities;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private String name;
    private String author;
    private String genre;
    private int count;
    private int id;

    public Book(){
    }

    public Book(String name, String author, String genre, int count, int id){
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.count = count;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Book))
            return false;
        Book book = (Book) o;
        return id == book.id && name.equals(book.name) && author.equals(book.author) && genre.equals(book.genre)
                && count == book.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, genre, count);
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", name='" + name + "', author='" + author +
                "', genre='" + genre + "', count=" + count + '}';
    }
}
