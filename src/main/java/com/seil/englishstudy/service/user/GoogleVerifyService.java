package com.seil.englishstudy.service.user;

import com.seil.englishstudy.vo.GoogleProfile;

public interface GoogleVerifyService {

    GoogleProfile verify(final String idToken);
}
