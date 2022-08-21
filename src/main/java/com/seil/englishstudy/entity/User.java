package com.seil.englishstudy.entity;

import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.FavoriteException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> roleList = new HashSet<>();

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Builder.Default
    private Set<Favorite> favoriteList = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return ""; }

    @Override
    public String getUsername() { return String.valueOf(this.id); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public void addFavorite(Favorite favorite) {
        favorite.setUser(this);
        if (this.getFavoriteList().contains(favorite) == true)
            throw new FavoriteException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "already exist data.");
        this.getFavoriteList().add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        if (this.getFavoriteList().contains(favorite) == false)
            throw new FavoriteException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "the user not have the favorite data.");
        this.getFavoriteList().remove(favorite);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        User data = (User) obj;
        return Objects.equals(this.getId(), data.getId())
                && Objects.equals(this.getEmail(), data.getEmail())
                && Objects.equals(this.getName(), data.getName())
                && Objects.equals(this.getRoleList(), data.getRoleList())
                && Objects.equals(this.getFavoriteList(), data.getFavoriteList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, roleList, favoriteList);
    }
}