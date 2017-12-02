package be.jorandeboever.domain;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "SPR_PERSON")
public class Person extends DomainObject implements UserDetails {
    private static final long serialVersionUID = 5785316997429556915L;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PERSON_UUID", nullable = false)
    private final Collection<Authority> authorities = new ArrayList<>();

    public Person() {
    }

    public Person(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return this.authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    @Override
    public String toString() {
        return this.username;
    }
}
