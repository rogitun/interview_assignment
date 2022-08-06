package nts.assignment.service;

import nts.assignment.config.AESCrypto;
import nts.assignment.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @Test
    public void test() throws Exception {
        String a = "abc1234";
        String encrypt = passwordEncoder.encode(a);

        System.out.println(encrypt);

        System.out.println(passwordEncoder.matches("abc1234",encrypt));

    }
}