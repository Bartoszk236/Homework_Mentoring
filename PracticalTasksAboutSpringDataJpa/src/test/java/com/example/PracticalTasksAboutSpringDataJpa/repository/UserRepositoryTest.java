package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Post;
import com.example.PracticalTasksAboutSpringDataJpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TestEntityManager em;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setNickname("User1");
        em.persist(user1);

        user2 = new User();
        user2.setNickname("User2");
        em.persist(user2);

        user3 = new User();
        user3.setNickname("User3");
        em.persist(user3);

        Post post1 = new Post();
        post1.setTitle("Financial");
        post1.setContent("Post1 content");
        post1.setCreatedDate(LocalDate.of(2020, 1, 1));
        post1.setUser(user1);
        em.persist(post1);

        Post post2 = new Post();
        post2.setTitle("History");
        post2.setContent("Post2 content");
        post2.setCreatedDate(LocalDate.of(2022, 1, 1));
        post2.setUser(user2);
        em.persist(post2);

        Post post3 = new Post();
        post3.setTitle("Financial");
        post3.setContent("Post3 content");
        post3.setCreatedDate(LocalDate.of(2021, 1, 1));
        post3.setUser(user3);
        em.persist(post3);

        em.flush();
        em.clear();
    }

    @Test
    void test_findByPostsTitle() {
        //given
        String givenPostTitle = "Financial";
        List<User> expected = List.of(user1, user3);

        //when
        List<User> result = repository.findByPostsTitle(givenPostTitle);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void test_findByPostsCreatedDateBetween() {
        //given
        LocalDate givenStartDate = LocalDate.of(2020, 6, 6);
        LocalDate givenEndDate = LocalDate.of(2022, 6, 6);
        List<User> expected = List.of(user2, user3);

        //when
        List<User> result = repository.findByPostsCreatedDateBetween(givenStartDate, givenEndDate);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }
}
