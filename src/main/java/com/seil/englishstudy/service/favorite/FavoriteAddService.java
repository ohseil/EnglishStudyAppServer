package com.seil.englishstudy.service.favorite;

import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.entity.Favorite;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import com.seil.englishstudy.repository.UserRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoriteAddService {

    private final EngStudyDataRepository engStudyDataRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long addFavoriteForUser(final Long engStudyDataId, final User user) {

        Optional<EngStudyData> engStudyData = engStudyDataRepository.findById(engStudyDataId);

        engStudyData.orElseThrow(() -> new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        Favorite favorite = Favorite.builder()
                .engStudyData(engStudyData.get())
                .build();

        user.addFavorite(favorite);
        userRepository.flush();

        return favorite.getId();
    }

}
