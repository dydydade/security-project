package com.example.securityproject.user.service;

import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.exception.AlreadyRegisteredUserException;
import com.example.securityproject.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("기존에 동일한 ID를 가진 사용자가 있을 경우 예외가 반환된다.")
    void signUpWithExistingUsernameShouldThorwException() {
        // given
        String username = "user";
        String password = "user";
        String authority = "ROLE_USER";
        User existingUser = new User(username, password, authority);

        when(userRepository.findByUsername(username)).thenReturn(existingUser);

        // when
        // then
        assertThatThrownBy(() -> {
            userService.signUp(username, password, authority);
        }).isInstanceOf(AlreadyRegisteredUserException.class);
    }

    @Test
    @DisplayName("기존에 동일한 ID를 가진 사용자가 없을 경우 사용자가 저장된다.")
    void signUpWithNewUsernameShouldSaveUser() {
        // given
        String username = "user";
        String password = "user";
        String authority = "ROLE_USER";
        User newUser = new User(username, password, authority);

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.save(any())).thenReturn(newUser);

        when(passwordEncoder.encode(any())).thenReturn("encodedPasswordFake");

        assertThat(userService.signUp(username, password, authority))
                .isEqualTo(newUser);
    }

    @Test
    @DisplayName("username 을 통해 저장된 user 를 조회할 수 있다.")
    void findByUsernameShouldReturnSavedUser() {
        // given
        String username = "user";
        String password = "user";
        String authority = "ROLE_USER";
        User existingUser = new User(username, password, authority);

        when(userRepository.findByUsername(username)).thenReturn(existingUser);

        // when
        // then
        assertThat(userService.findByUsername(username)).isEqualTo(existingUser);
    }
}














