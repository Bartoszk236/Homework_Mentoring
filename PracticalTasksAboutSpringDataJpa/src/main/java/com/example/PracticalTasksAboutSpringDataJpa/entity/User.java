package com.example.PracticalTasksAboutSpringDataJpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Setter
    @Column(name = "nickname")
    private String nickname;

    @Setter
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Setter
    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.REMOVE)
    private Set<Post> posts = new HashSet<>();

    void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
