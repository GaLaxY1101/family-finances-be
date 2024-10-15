package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.RoleName;
import com.mindspark.family_finances.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

//    @BeforeEach
//    public void setUp(){
//        userRepository.deleteAll();
//    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenUserObject_whenSave_thenUserIsCreated(){
        //given
        User userToSave = User.builder()
                .firstname("Albert")
                .lastname("Einstein")
                .email("alberteinstein@gmail.com")
                .password("12345678")
                .role(RoleName.PARENT)
                .build();
        //when
        User savedUser = userRepository.save(userToSave);

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }
}
