package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Post;
import com.example.PracticalTasksAboutSpringDataJpa.entity.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TestEntityManager em;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            em.persist(randomUser());
            em.persist(randomPost());
        }

        em.flush();
        em.clear();
    }

    @Test
    void test_findByPostsTitle() {
        //given
        String givenPostTitle = faker.regexify("[a-zA-Z]{15}");
        Post post = randomPost(post1 ->  post1.setTitle(givenPostTitle));
        em.persist(post);
        em.flush();
        em.clear();

        //when
        List<User> result = repository.findByPostsTitle(givenPostTitle);

        //then
        result.stream()
                .map(User::getPosts)
                .flatMap(Collection::stream)
                .map(Post::getTitle)
                .forEach(title -> assertThat(title).isEqualTo(givenPostTitle));
    }

    @Test
    void test_findByPostsCreatedDateBetween() {
        //given
        LocalDate givenStartDate = LocalDate.now().minusMonths(3);
        LocalDate givenEndDate = LocalDate.now().minusMonths(1);

        Post post = randomPost(post1 ->  post1.setCreatedDate(LocalDate.now().minusMonths(2)));
        em.persist(post);
        em.flush();
        em.clear();

        //when
        List<User> result = repository.findByPostsCreatedDateBetween(givenStartDate, givenEndDate);

        //then
        result.stream()
                .map(User::getPosts)
                .flatMap(Collection::stream)
                .map(Post::getCreatedDate)
                .forEach(dateCreated -> assertThat(dateCreated).isBetween(givenStartDate, givenEndDate));
    }

    @Test
    void test_deleteUsersByLastLoginBefore() {
        //given
        LocalDateTime givenBefore = LocalDateTime.now().minusMonths(6);

        //when
        repository.deleteUsersByLastLoginBefore(givenBefore);
        em.flush();
        em.clear();

        //then
        List<User> result = repository.findAll();
        result.stream()
                .map(User::getLastLogin)
                .forEach(lastLogin -> assertThat(lastLogin).isAfter(givenBefore));
    }

    @Test
    void test_deactivateInactiveUsers() {
        //given
        LocalDateTime givenCutoffDate = LocalDateTime.now().minusMonths(1);

        //when
        int results = repository.deactivateInactiveUsers(givenCutoffDate);

        //then
        List<User> result = repository.findAll();
        result.stream()
                .filter(user ->  user.getLastLogin().isBefore(givenCutoffDate))
                .forEach(user -> assertEquals(false, user.getActive()));
    }

    private User randomUser() {
        User user = new User();
        user.setNickname(faker.name().username());
        int random = faker.number().numberBetween(0, 1);
        user.setActive(random != 0);
        int random2 = faker.number().numberBetween(1, 11);
        user.setLastLogin(LocalDateTime.now().minusMonths(random2));
        return user;
    }

    private Post randomPost() {
        Post post = new Post();
        post.setTitle(faker.book().title());
        post.setContent(faker.regexify("[a-zA-Z]{50}"));
        int random =  faker.number().numberBetween(1, 11);
        post.setCreatedDate(LocalDate.now().minusMonths(random).minusDays(random));

        User user = randomUser();
        em.persist(user);
        post.setUser(user);

        return post;
    }

    private Post randomPost(Consumer<Post> customizer) {
        Post post = randomPost();
        customizer.accept(post);
        return post;
    }
}
