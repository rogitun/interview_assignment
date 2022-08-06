package nts.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.Hashtag;
import nts.assignment.domain.HashtagPost;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.PostForm;
import nts.assignment.repository.hashtag.HashtagRepository;
import nts.assignment.repository.hashtag.TagPostRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final TagPostRepository tagPostRepository;

    @Transactional
    public void addPost(PostForm postForm) {
        Post post = makeNewPost(postForm);
        makeHashTags(postForm.getHashtag(),post);
    }

    private Post makeNewPost(PostForm postForm) {
        Post build = Post.builder()
                .title(postForm.getTitle())
                .writer(postForm.getWriter())
                .password(postForm.getPassword()) //TODO 암호화 필요함
                .content(postForm.getContent())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now()).build();
        postRepository.save(build);
        return build;
    }

    private void makeHashTags(String hashtag,Post post) {
        String[] hashtags = hashtag.split("#");//해쉬태그 구분
        for (String s : hashtags) {
            s.replaceAll(" ","");//공백 제거
            Optional<Hashtag> hashTag = hashtagRepository.findByName(s);

            if(hashTag.isPresent()){ //기존에 존재하는 hashTag, 연관관계 매핑
                Hashtag originHashTag = hashTag.get();
                HashtagPost hashtagPost = new HashtagPost(originHashTag, post);
                tagPostRepository.save(hashtagPost);
            }
            else{ //처음 등록된 해쉬태그
                Hashtag newHashTag = new Hashtag(s);
                hashtagRepository.save(newHashTag);
                HashtagPost hashtagPost = new HashtagPost(newHashTag, post);
                tagPostRepository.save(hashtagPost);
            }
        }
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
