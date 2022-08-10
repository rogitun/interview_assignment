package nts.assignment.controller;

import nts.assignment.domain.Comment;
import nts.assignment.domain.Post;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.post.PostRepository;
import nts.assignment.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostControllerTest {

    @Autowired
    PostService postService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @Transactional
    public void delComment(){
        Post build = Post.builder()
                .content("asd")
                .title("asdasd")
                .writer("asdasd")
                .password(passwordEncoder.encode("sadasd")).build();

        postRepository.save(build);

        Comment comment = new Comment("cw",passwordEncoder.encode("1234"),"1234512",build);

        Comment save = commentRepository.save(comment);

       // postService.delComment(save.getCommentId(),"1234");

        em.flush();
        em.clear();

        Optional<Comment> byId = commentRepository.findById(save.getCommentId());

        Comment comment1 = byId.get();

        Assertions.assertThat(comment1.getContent()).isEqualTo("삭제된 댓글입니다.");
    }
}