package nts.assignment.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import nts.assignment.controller.dto.SearchCond;
import nts.assignment.domain.*;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.hashtag.HashtagRepository;
import nts.assignment.repository.hashtag.TagPostRepository;
import nts.assignment.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static nts.assignment.domain.QHashtag.hashtag;
import static nts.assignment.domain.QHashtagPost.hashtagPost;
import static nts.assignment.domain.QPost.post;

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

    @Autowired
    JPAQueryFactory jpaQueryFactory;


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
    void hashTag() {
        Hashtag hashtag = new Hashtag();

//        hashtag.setName("testHash");
//
//        hashtagRepository.save(hashtag);
//
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

    @BeforeEach
    public void init(){
        Hashtag hashtag = new Hashtag("testHash");
        hashtagRepository.save(hashtag);
        Hashtag hashtag2 = new Hashtag("testHash2");
        hashtagRepository.save(hashtag2);
        Hashtag hashtag3 = new Hashtag("testHash3");
        hashtagRepository.save(hashtag3);

        Post post = Post.builder()
                .title("testTitle")
                .content("testContent")
                .password("1234")
                .writer("tester")
                .created(LocalDateTime.now()).build();
        postRepository.save(post);
        Post post2 = Post.builder()
                .title("testTitleSecond")
                .content("testContentSecond")
                .password("1234")
                .writer("testerSecond")
                .created(LocalDateTime.now()).build();
        postRepository.save(post2);
        Post post3 = Post.builder()
                .title("testTitleThird")
                .content("testContentThird")
                .password("1234")
                .writer("testerThird")
                .created(LocalDateTime.now()).build();
        postRepository.save(post3);

        HashtagPost hashtagPost = new HashtagPost(hashtag, post);
        tagPostRepository.save(hashtagPost);

        HashtagPost hashtagPost2 = new HashtagPost(hashtag2, post2);
        tagPostRepository.save(hashtagPost2);

        HashtagPost hashtagPost3 = new HashtagPost(hashtag3, post3);
        tagPostRepository.save(hashtagPost3);

    }

    @Test
    public void searchTest() {

//        //?????? ?????? ?????????
//        SearchCond searchCond = new SearchCond();
//        searchCond.setTitle("Second");
//
//        PageRequest pageRequest = PageRequest.of(0,6);
//
//        Post post = jpaQueryFactory.select(QPost.post)
//                .from(QPost.post)
//                .leftJoin(QPost.post.hashtags, hashtagPost).fetchJoin()
//                .leftJoin(hashtagPost.hashtag, hashtag).fetchJoin()
//                .where(QPost.post.title.contains(searchCond.getTitle()))
//                .fetchOne();
//
//        List<HashtagPost> hashtags = post.getHashtags();
//        for (HashtagPost hashtag : hashtags) {
//            Hashtag hashtag1 = hashtag.getHashtag();
//            System.out.println(hashtag1.getName());
//        }


        //?????? ?????? ????????? ???????????? ???????????? ??????
    }

    @Test
    public void countTest(){
        List<Tuple> fetch = jpaQueryFactory.select(post.count(), hashtag.count()).distinct()
                .from(post, hashtag)
                .fetch();

        for (Tuple tuple : fetch) {
            System.out.println(tuple.toString());
        }
    }
}