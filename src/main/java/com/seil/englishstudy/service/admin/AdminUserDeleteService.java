package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.UserDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminUserDeleteService {

    private final UserRepository userRepository;

    @Transactional
    public void deleteAdminUser(final String adminEmail) {

        User user = userRepository.findByEmail(adminEmail);

        if (user == null)
            throw new UserDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");

        if (user.getRoleList().contains("ROLE_ADMIN") == false)
            throw new UserDataException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "not admin user.");

        userRepository.delete(user);
    }
}
