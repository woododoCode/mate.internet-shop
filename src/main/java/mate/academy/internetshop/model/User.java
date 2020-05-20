package mate.academy.internetshop.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class User {
    private Long userId;
    private String name;
    private String login;
    private String password;
    private byte[] salt;
    private Set<Role> roles;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + ", salt=" + Arrays.toString(salt)
                + ", roles=" + roles
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId)
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getLogin(), user.getLogin())
                && Objects.equals(getPassword(), user.getPassword())
                && Arrays.equals(getSalt(), user.getSalt())
                && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, getName(), getLogin(), getPassword(), getRoles());
        result = 17 * result + Arrays.hashCode(getSalt());
        return result;
    }
}
