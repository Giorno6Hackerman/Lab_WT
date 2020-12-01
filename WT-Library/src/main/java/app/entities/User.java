package app.entities;

public class User {
    private String name;
    private String surname;
    private String login;
    private String passwordHash;

    public User(){
    }

    public User(String name, String surname, String login, String passwordHash){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void PasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
}
