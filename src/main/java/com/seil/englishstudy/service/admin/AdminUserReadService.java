package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.response.UserReadResponse;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.UserDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserReadService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserReadResponse> readAllUser() {

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty() == true)
            throw new UserDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");

        List<UserReadResponse> userReadResponseList = new ArrayList<>();

        for (User user : userList) {
            userReadResponseList.add(UserReadResponse.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .roleList(user.getRoleList())
                    .createdDate(user.getCreatedDate())
                    .favoriteList(user.getFavoriteList())
                    .build());
        }

        return userReadResponseList;
    }
}
