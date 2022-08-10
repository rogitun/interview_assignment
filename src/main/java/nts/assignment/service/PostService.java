package nts.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.controller.dto.SearchCond;
import nts.assignment.domain.*;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.EditForm;
import nts.assignment.domain.form.PostForm;
import nts.assignment.repository.AnonymousLikeRepository;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.hashtag.HashtagRepository;
import nts.assignment.repository.hashtag.TagPostRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final TagPostRepository tagPostRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AnonymousLikeRepository anonymousLikeRepository;
    private final CommentRepository commentRepository;

    private final int DAY = 86400;
    private final int MINUTE = 60;

    @Transactional
    public void addPost(PostForm postForm) {
        Post post = makeNewPost(postForm);
        if (StringUtils.hasText(postForm.getHashtag()))
            makeHashTags(postForm.getHashtag(), post);
    }

    private Post makeNewPost(PostForm postForm) {
        Post build = Post.builder()
                .title(postForm.getTitle())
                .writer(postForm.getWriter())
                .password(passwordEncoder.encode(postForm.getPassword())) //TODO 암호화 필요함
                .content(postForm.getContent())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now()).build();
        postRepository.save(build);
        return build;
    }

    private void makeHashTags(String hashtag, Post post) {
        String[] hashtags = hashtag.split("#");//해쉬태그 구분
        hashtags = Arrays.stream(hashtags).map(s -> s = s.replaceAll(" ", "")).toArray(String[]::new);
        hashtags = Arrays.stream(hashtags).distinct().toArray(String[]::new);

        for (String s : hashtags) {
            //  s = s.replaceAll(" ", "");//공백 제거
            Optional<Hashtag> hashTag = hashtagRepository.findByName(s);

            if (hashTag.isPresent()) { //기존에 존재하는 hashTag, 연관관계 매핑
                Hashtag originHashTag = hashTag.get();
                HashtagPost hashtagPost = new HashtagPost(originHashTag, post);
                tagPostRepository.save(hashtagPost);
            } else { //처음 등록된 해쉬태그
                Hashtag newHashTag = new Hashtag(s);
                hashtagRepository.save(newHashTag);
                HashtagPost hashtagPost = new HashtagPost(newHashTag, post);
                tagPostRepository.save(hashtagPost);
            }
        }
    }

    public Page<MainPostDto> getAllPost(Pageable pageable, SearchCond cond) {
        return postRepository.getAllPost(pageable, cond);
    }

    @Transactional
    public SinglePostDto getSinglePost(Long id) {
        Optional<SinglePostDto> singlePost = postRepository.getSinglePost(id);
        postRepository.updateView(id);
        return singlePost.orElseThrow();
    }

    @Transactional
    public void delPost(Long id, String password) {
        //비밀번호 일치 확인
        log.info("param = {}, {}", id, password);
      //  Optional<Post> byId = postRepository.findById(id);
        String postPassword = postRepository.findPasswordById(id);
        if (postPassword==null) throw new NoSuchElementException();

        //Post post = byId.get();

        //비밀번호 일치 확인
        if (passwordEncoder.matches(password, postPassword)) {
            commentRepository.deleteByPostId(id);
            tagPostRepository.deleteByPostId(id); //delete 쿼리
            postRepository.deleteByPostId(id);
        } else throw new NoSuchElementException();
    }

    public List<String> getHashTags(Long id) {
        return hashtagRepository.findHashTagByPostId(id);
    }

    public PostFormDto getEditingPost(Long id) {
        Optional<PostFormDto> editForm = postRepository.findPostByPassword(id);
        return editForm.orElseThrow();
    }

    public String getHashTagsString(Long id) {
        List<String> hastTags = hashtagRepository.findHashTagByPostId(id);
        StringBuilder hash = new StringBuilder();
        for (String hastTag : hastTags) {
            hash.append("#").append(hastTag);
        }
        return hash.toString();
    }

    @Transactional
    public void editPost(Long id, EditForm obj) {
        //id, obj.pwd 값 비교해서 기존 엔티티 존재 여부 찾는다.
        Optional<Post> post = postRepository.findPostByCredential(id, obj.getPwd());
        if (post.isEmpty()) throw new NoSuchElementException();

        //존재하면
        Post curPost = post.get();
        curPost.editPost(obj.getTitle(), obj.getContent(), passwordEncoder.encode(obj.getPassword()), obj.getWriter());

        //기존 해시태그 삭제하고 새로 등록한다.
        if(StringUtils.hasText(obj.getHashtag())) {
            tagPostRepository.deleteByPostId(id);
            makeHashTags(obj.getHashtag(), curPost);
        }
    }

    public boolean countByPassword(Long id, String password) {
        String encodePwd = postRepository.findPasswordById(id);
        return passwordEncoder.matches(password, encodePwd);
    }

    public Long countAllPost() {
        return postRepository.count();
    }

    @Transactional
    public boolean likePost(Long id, String ip) {
        //4가지 상황
        //내가 아직 추천을 안눌러서 추천이 가능한 상황. => add
        //내가 추천을 눌렀지만 하루가 지나서 추천이 가능한 상황 => add
        //내가 추천을 눌렀고 하루가 안지나서 추천이 불가능한 상황 => ?
        //추천하려 했으나 게시글이 삭제되어 추천이 불가능한 상황 => Throw

        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.orElseThrow(); // => 게시글 삭제된 상황.

        Optional<AnonymousLike> anonymousLike = anonymousLikeRepository.countByPostId(id, ip);
        if (anonymousLike.isEmpty()) { //내가 아직 추천을 안눌러서 추천이 가능함.
            postRepository.updateLike(id);
            AnonymousLike newAddrLike = new AnonymousLike(ip, post);
            anonymousLikeRepository.save(newAddrLike);
            return true;
        }

        AnonymousLike postLiked = anonymousLike.get();
        if (timePassCheck(postLiked.getActionDate())) {//내가 추천을 눌렀지만 하루가 지나서 추천이 가능한 상황 => add
            postLiked.addSamePost();
            postRepository.updateLike(id);
            return true;
        }
        return false; //내가 추천을 눌렀고 하루가 안지나서 추천이 불가능한 상황 => ?
    }

    @Transactional
    public boolean disLikePost(Long id, String ip) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.orElseThrow(); // => 게시글 삭제된 상황.

        Optional<AnonymousLike> anonymousLike = anonymousLikeRepository.countByPostId(id, ip);
        if (anonymousLike.isEmpty()) { //내가 아직 추천을 안눌러서 추천이 가능함.
            postRepository.updateDisLike(id);
            AnonymousLike newAddrLike = new AnonymousLike(ip, post);
            anonymousLikeRepository.save(newAddrLike);
            return true;
        }

        AnonymousLike postLiked = anonymousLike.get();
        if (timePassCheck(postLiked.getActionDate())) {//내가 추천을 눌렀지만 하루가 지나서 추천이 가능한 상황 => add
            postLiked.addSamePost();
            postRepository.updateDisLike(id);
            return true;
        }
        return false; //내가 추천을 눌렀고 하루가 안지나서 추천이 불가능한 상황 => ?
    }

    private boolean timePassCheck(LocalDateTime actionDate) {
        Duration duration = Duration.between(actionDate, LocalDateTime.now());
        return duration.getSeconds() > DAY;
    }
}
