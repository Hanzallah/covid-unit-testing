package com.covidunit.covidunitapi.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserModel {
    private @Id @GeneratedValue long id;
    private @NotBlank @NotNull String name;
    private @NotBlank @NotNull String email;
    private @NotBlank @NotNull String password;
    private @NotBlank boolean loggedIn;

    public UserModel(){}
    public UserModel(@NotBlank @NotNull String name, @NotBlank @NotNull String email,
                @NotBlank @NotNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loggedIn = false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModel)) return false;
        UserModel user = (UserModel) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, loggedIn);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn + '}';
    }

}
