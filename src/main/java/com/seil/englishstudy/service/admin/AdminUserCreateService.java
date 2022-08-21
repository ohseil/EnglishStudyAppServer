package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminUserCreateRequest;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AdminUserCreateService {

    private final UserRepository userRepository;

    public Long createAdminUser(final AdminUserCreateRequest adminUserCreateRequest) {

        User user = User.builder()
                .email(adminUserCreateRequest.getEmail())
                .name(adminUserCreateRequest.getName())
                .roleList(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                .createdDate(LocalDateTime.now())
                .build();

        return userRepository.save(user).getId();
    }
}
