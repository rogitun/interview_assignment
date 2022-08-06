package nts.assignment;

import lombok.RequiredArgsConstructor;
import nts.assignment.domain.Post;
import nts.assignment.repository.post.PostRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Dummies {

    private final PostRepository postRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        Post post = Post.builder()
                .password("1234")
                .writer("tester")
                .content("ㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴㅇㅁㅈㄷㅂㅈㄷㅂㅈㅇㅈㅂㅁㄴㅇㅁㄴㅇ")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .title("제목").build();
        postRepository.save(post);
    }

}
