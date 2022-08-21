package com.seil.englishstudy.repository;

import com.seil.englishstudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(final String email);
}
