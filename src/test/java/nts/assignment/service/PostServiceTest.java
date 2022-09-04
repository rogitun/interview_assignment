package nts.assignment.service;

import nts.assignment.domain.Comment;
import nts.assignment.domain.Post;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    CommentRepository commentRepository;


    @Test
    public void test() {
        String a = "abc1234";
        String encrypt = passwordEncoder.encode(a);

        System.out.println(encrypt);

        System.out.println(passwordEncoder.matches("abc1234",encrypt));
    }


    @Test
    @Transactional
    public void addComment(){
        Post post = Post.builder()
                .title("test")
                .content("testCont")
                .password("1234")
                .writer("wrtier")
                .build();
        Post save = postRepository.save(post);

        Comment comment = new Comment("cwriter","cpwd","ccont",post);
        Comment save1 = commentRepository.save(comment);

        Optional<Post> byId = postRepository.findById(save.getPostId());

        Assertions.assertThat(byId.isPresent()).isTrue();
        Post post1 = byId.get();
        Assertions.assertThat(post1.getComments().size()).isEqualTo(1);

        Assertions.assertThat(save1.getPost()).isNotNull();
    }
}