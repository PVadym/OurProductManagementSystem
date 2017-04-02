package com.management.product.entity;

import com.management.product.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The entity class, describe user entity, implements a set of standard methods for working with this entity.
 * extends Model
 *
 * @author Slava
 */
@Entity
@Table(name = "users")
public class User extends Model implements UserDetails {

    /**
     * User's name
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * User's password
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * User's role
     */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * This field has information about locking user's account. If isLocked == false, then account isn't locked,
     * if isLocked == true, then account is locked
     */
    @Column(name = "locked")
    private boolean isLocked;

    /**
     * Default constructor
     */
    public User() {
        username = "";
        password = "";
        role = UserRole.USER;
    }

    /**
     * Constructor
     *
     * @param username user's name
     */
    public User(String username) {
        this();
        setUsername(username);
    }

    /**
     * Constructor
     *
     * @param username user's name
     * @param password user's password
     */
    public User(String username, String password) {
        this(username);
        setPassword(password);
    }

    /**
     * Constructor
     *
     * @param username user's name
     * @param password user's password
     * @param role user's role
     */
    public User(String username, String password, UserRole role) {
        this(username, password);
        setRole(role);
    }

    /**
     * Method for getting string representation of instances of the User class.
     *
     * @return a string representation of the instance.
     */
    @Override
    public String toString() {
        return "User{" + super.toString() +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isLocked=" + isLocked +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument
     */
    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        User user = (User) o;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        return role == user.role;
    }

    /**
     * Method for getting a hashcode value of the instance
     *
     * @return an integer, hash code value of the instance
     */
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    /**
     * An overridden method from UserDetails interface. The method checks user's account expiring.
     *
     * @return true if account is't expired, or false otherwise. In our case returns not isLocked value.
     */
    @Override
    public boolean isAccountNonExpired() {
        return !isLocked;
    }

    /**
     * An overridden method from UserDetails interface. The method checks user's account locking.
     *
     * @return true if account is't locked, or false otherwise. In our case returns not isLocked value.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    /**
     * An overridden method from UserDetails interface. The method checks expiring of user's account credentials.
     *
     * @return true if account's credentials is't expired, or false otherwise. In our case returns not isLocked value.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return !isLocked;
    }

    /**
     * An overridden method from UserDetails interface. The method checks user's account is enabled, or not.
     *
     * @return true if account is enabled, or false otherwise. In our case returns not isLocked value.
     */
    @Override
    public boolean isEnabled() {
        return !isLocked;
    }


    /**
     * Method for getting collection of user's authorities. An overridden method from UserDetails interface.
     *
     * @return a collection of user's authorities. In our case the collection has just one entry,
     * and the entry contains information about user's role.
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + getRole().name());
        grantedAuthorities.add(simpleGrantedAuthority);
        return grantedAuthorities;
    }

    /**
     * A getter for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A setter for the username.
     * If parameter is null or whitespace, method sets empty string as username.
     *
     * @param username a user's name
     */
    public void setUsername(String username) {
        this.username = isNotBlank(username) ? username : "";
    }

    /**
     * A getter for the password
     *
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A setter for the password.
     * If parameter is null or whitespace, method sets empty string as password.
     *
     * @param password a user's password.
     */
    public void setPassword(String password) {
        this.password = isNotBlank(password) ? password : "";
    }

    /**
     * A getter for the field role
     *
     * @return user's role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * A setter for the field role.
     * If parameter is null, method sets 'USER' as role.
     *
     * @param role a user's role.
     */
    public void setRole(UserRole role) {
        this.role = role != null ? role : UserRole.USER;
    }

    /**
     * A getter for the field isLocked
     *
     * @return value of the field isLocked, a boolean value of locking user's account
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * A setter for the field isLocked.
     *
     * @param locked a boolean value of locking user's account
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

}
