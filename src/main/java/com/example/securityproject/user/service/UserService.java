package com.example.securityproject.user.service;

import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.exception.AlreadyRegisteredUserException;
import com.example.securityproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록
     * @param username
     * @param password
     * @return 인자로 넘겨받은 권한(유저/관리자 등)을 가지고 있는 유저
     */
    public User signUp(String username, String password, String authority) {
        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }
        return userRepository.save(new User(username, password, authority));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
