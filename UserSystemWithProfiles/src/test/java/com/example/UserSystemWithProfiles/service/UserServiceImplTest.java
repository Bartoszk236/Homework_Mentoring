package com.example.UserSystemWithProfiles.service;

import com.example.UserSystemWithProfiles.entity.User;
import com.example.UserSystemWithProfiles.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UserServiceImpl.class)
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        em.clear();
    }

    @Test
    void shouldCreateUserWithProfile() {
        //given
        String testEmail = "X";
        String testPassword = "Y";
        String testFirstName = "John";
        String testLastName = "Doe";

        //when
        userService.createUserWithProfile(testEmail, testPassword, testFirstName, testLastName);
        em.flush();
        em.clear();

        //then
        User userResult = userService.findByEmail(testEmail);

        assertNotNull(userResult);
        assertEquals(testEmail, userResult.getEmail());
        assertEquals(testPassword, userResult.getPassword());

        UserProfile userProfileResult = userResult.getUserProfile();
        assertNotNull(userProfileResult);
        assertEquals(testFirstName, userProfileResult.getFirstName());
        assertEquals(testLastName, userProfileResult.getLastName());
        assertSame(userResult, userProfileResult.getUser());
    }

    @Test
    void shouldDeleteUserWithProfile() {
        //given
        User testUser = new User()
                .setEmail("X")
                .setPassword("Y")
                .linkUserProfile(
                        new UserProfile()
                                .setFirstName("John")
                                .setLastName("Doe")
                );
        em.persist(testUser);
        em.flush();
        em.clear();

        //when
        userService.deleteUser(testUser.getId());
        em.flush();
        em.clear();

        //then
        Long usersCount = em.getEntityManager()
                .createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
        Long usersProfilesCount = em.getEntityManager()
                .createQuery("select count(p) from UserProfile p", Long.class)
                .getSingleResult();

        assertEquals(0, usersCount);
        assertEquals(0, usersProfilesCount);
    }
}
