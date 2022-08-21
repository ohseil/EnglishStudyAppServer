package com.seil.englishstudy.dto.request;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class AdminUserCreateRequest {

    @Email
    private final String email;
    private final String name;
}
