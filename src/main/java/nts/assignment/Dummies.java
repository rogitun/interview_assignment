package nts.assignment;

import lombok.RequiredArgsConstructor;
import nts.assignment.domain.Comment;
import nts.assignment.domain.Post;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Dummies {

    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){

        Post post = Post.builder()
                .password(passwordEncoder.encode("1234"))
                .writer("FirstWriter")
                .content("ㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇ")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .title("제목").build();
        postRepository.save(post);

        for(int i=0;i<23;i++) {
            Post post2 = Post.builder()
                    .password(passwordEncoder.encode("1234"))
                    .writer("tester"+i)
                    .content("ㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇ"+i)
                    .created(LocalDateTime.now())
                    .modified(LocalDateTime.now())
                    .title("제목").build();
            postRepository.save(post2);
        }

        for(int i=0;i<5;i++) {
            Comment comment = new Comment("writer"+i,passwordEncoder.encode("pwd"+i),"content"+i,post);
            commentRepository.save(comment);
        }
    }

}
