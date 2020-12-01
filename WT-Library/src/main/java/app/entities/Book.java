package app.entities;

public class Book {
    private String name;
    private String author;
    private String genre;
    private int year;

    public Book(){
    }

    public Book(String name, String author, String genre, int year){
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
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

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }
}
