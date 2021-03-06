package app.entities;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;

    public User(){
    }

    public User(int id, String name, String surname, String login, String password, Role role){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && surname.equals(user.surname) && login.equals(user.login)
                && password.equals(user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, role);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', surname='" + surname +
                "', login='" + login + "', password='" + password + "', role=" + role + '}';
    }
}
