package com.example.JsonViewsAndConditionalSerialization.service;

import com.example.JsonViewsAndConditionalSerialization.entity.UserProfile;
import com.example.JsonViewsAndConditionalSerialization.repository.ProfileRepository;
import com.example.JsonViewsAndConditionalSerialization.view.Views;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public MappingJacksonValue getProfile(Long profileId, String role) {
        UserProfile userProfile = profileRepository.findById(profileId).orElseThrow(EntityNotFoundException::new);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userProfile);

        if (role != null) {
            if (role.contains("Admin")) mappingJacksonValue.setSerializationView(Views.Admin.class);
            else if (role.contains("Owner")) mappingJacksonValue.setSerializationView(Views.Owner.class);
            else mappingJacksonValue.setSerializationView(Views.Public.class);
        } else mappingJacksonValue.setSerializationView(Views.Public.class);

        return mappingJacksonValue;
    }
}
