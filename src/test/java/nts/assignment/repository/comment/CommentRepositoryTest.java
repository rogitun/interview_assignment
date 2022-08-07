package nts.assignment.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import nts.assignment.domain.Comment;
import nts.assignment.domain.Post;
import nts.assignment.domain.QComment;
import nts.assignment.domain.QPost;
import nts.assignment.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static nts.assignment.domain.QComment.comment;
import static nts.assignment.domain.QPost.post;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getComments(){
        Post build = Post.builder()
                .writer("test")
                .password("1234")
                .title("abcd")
                .content("asdasdasd").build();


        Post save = postRepository.save(build);

        Comment comment = new Comment("cwr","cpwd","ctet",build);
        commentRepository.save(comment);
    }

}