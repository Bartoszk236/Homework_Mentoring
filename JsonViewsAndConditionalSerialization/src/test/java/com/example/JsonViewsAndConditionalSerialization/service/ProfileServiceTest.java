package com.example.JsonViewsAndConditionalSerialization.service;

import com.example.JsonViewsAndConditionalSerialization.entity.UserProfile;
import com.example.JsonViewsAndConditionalSerialization.repository.ProfileRepository;
import com.example.JsonViewsAndConditionalSerialization.view.Views;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfileServiceTest {
    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final ProfileService profileService = new ProfileService(profileRepository);
    private final UserProfile userProfile = UserProfile.builder()
            .id(1L)
            .firstName("John")
            .email("john@gmail.com")
            .phone("123456789")
            .ssn("12345")
            .lastLogin(LocalDateTime.of(2020, 1, 1, 0, 0))
            .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
            .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0))
            .active(true)
            .build();

    @Test
    void givenInvalidIdWhenGetProfileThenThrowException() {
        // given
        when(profileRepository.findById(42L)).thenReturn(Optional.empty());
        when(profileRepository.findById(42L)).thenThrow(EntityNotFoundException.class);

        // when/then
        assertThrows(EntityNotFoundException.class, () -> profileService.getProfile(42L, "Admin"));
    }

    @Test
    void givenValidIdAndAdminWhenGetProfileThenReturnProfile() {
        // given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

        // when
        MappingJacksonValue mappingJacksonValue = profileService.getProfile(1L, "Admin");

        // then
        assertSame(userProfile, mappingJacksonValue.getValue());
        assertEquals(Views.Admin.class, mappingJacksonValue.getSerializationView());
    }

    @Test
    void givenValidIdAndOwnerWhenGetProfileThenReturnProfile() {
        // given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

        // when
        MappingJacksonValue mappingJacksonValue = profileService.getProfile(1L, "Owner");

        // then
        assertSame(userProfile, mappingJacksonValue.getValue());
        assertEquals(Views.Owner.class, mappingJacksonValue.getSerializationView());
    }

    @Test
    void givenValidIdAndPublicWhenGetProfileThenReturnProfile() {
        // given
        when(profileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

        // when
        MappingJacksonValue mappingJacksonValuePublic = profileService.getProfile(1L, "Public");
        MappingJacksonValue mappingJacksonValueOther = profileService.getProfile(1L, null);

        // then
        assertSame(userProfile, mappingJacksonValuePublic.getValue());
        assertEquals(Views.Public.class, mappingJacksonValuePublic.getSerializationView());

        assertSame(userProfile, mappingJacksonValueOther.getValue());
        assertEquals(Views.Public.class, mappingJacksonValueOther.getSerializationView());
    }
}
