package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserNotFound() {
        Optional<User> result = userRepository.findByUsername("alice");
        assertThat(result).isNotPresent();
    }


    @Test
    void findByUsername_ShouldReturnUser() {
        User user = new User();
        user.setUsername("JavierAlvaradoTest");
        user.setEmail("alv21188@uvg.edu.gt");
        user.setPassword("123456789");

        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("JavierAlvaradoTest");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("alv21188@uvg.edu.gt");
    }

}
