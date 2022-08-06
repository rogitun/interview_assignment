package nts.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.PostForm;
import nts.assignment.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void addPost(PostForm postForm) {
        Post newPost = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .writer(postForm.getWriter())
                .password(postForm.getPassword())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();
        postRepository.save(newPost);
    }

    public Page<MainPostDto> getAllPost(Pageable pageable) {
        Page<MainPostDto> allPost = postRepository.getAllPost(pageable);
        return allPost;
    }

    public SinglePostDto getSinglePost(Long id) {
        Optional<SinglePostDto> singlePost = postRepository.getSinglePost(id);
        return singlePost.orElseThrow();
    }
}
