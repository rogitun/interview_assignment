package nts.assignment.repository;

import nts.assignment.domain.Comment;
import nts.assignment.domain.Hashtag;
import nts.assignment.domain.HashtagPost;
import nts.assignment.domain.Post;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.hashtag.HashtagRepository;
import nts.assignment.repository.hashtag.TagPostRepository;
import nts.assignment.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TagPostRepository tagPostRepository;


    @Test
    @Rollback(value = false)
    void createTest() {
//        Post post = new Post();
//
//        post.setTitle("test1");
//        post.setWriter("test2");
//        post.setPassword("test3");
//        post.setContent("test4");
//        post.setCreated(LocalDateTime.now());
//        post.setModified(LocalDateTime.now());
//
//        postRepository.save(post);
    }

    @Test
    @Rollback(value = false)
    void hashTag(){
        Hashtag hashtag = new Hashtag();

        hashtag.setName("testHash");

        hashtagRepository.save(hashtag);

        Post post = new Post();
//
//        post.setTitle("test1");
//        post.setWriter("test2");
//        post.setPassword("test3");
//        post.setContent("test4");
//        post.setCreated(LocalDateTime.now());
//        post.setModified(LocalDateTime.now());
//
//        postRepository.save(post);
//
//        HashtagPost hashtagPost = new HashtagPost(hashtag,post);
//
//        tagPostRepository.save(hashtagPost);
//
//        Comment comment = new Comment("tester","1234","hhihi");
//
//        commentRepository.save(comment);
//
//        comment.addComment(post);
//
//
//        Post posts = postRepository.getPost(post.getPostId());
//
//        Assertions.assertThat(posts.getComments().size()).isEqualTo(1);
    }
}