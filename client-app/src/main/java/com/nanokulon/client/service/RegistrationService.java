package com.nanokulon.client.service;

import com.nanokulon.client.controller.payload.NewUserPayload;
import com.nanokulon.client.entity.InspyroUser;
import com.nanokulon.client.repository.InspyroUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final InspyroUserRepository inspyroUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(NewUserPayload payload) {
        this.inspyroUserRepository.save(new InspyroUser(null, payload.username(),
                this.passwordEncoder.encode(payload.password()), null));
    }
}
