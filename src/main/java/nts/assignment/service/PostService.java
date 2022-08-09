package nts.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.controller.dto.SearchCond;
import nts.assignment.domain.*;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.CommentForm;
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
    private final CommentRepository commentRepository;
    private final AnonymousLikeRepository anonymousLikeRepository;

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

    //TODO 쿼리 과다
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
        // Optional<Post> postWithComments = postRepository.findPostWithComments(id);
        Optional<SinglePostDto> singlePost = postRepository.getSinglePost(id);
        postRepository.updateView(id);
        return singlePost.orElseThrow();
    }

//    @Transactional
//    public Post getSinglePost(Long id) {
//        Optional<Post> postWithComments = postRepository.findPostWithComments(id);
//        Post post = postWithComments.orElseThrow();
//        postRepository.updateView(id);
//        // post.updateView();
//        return post;
//    }

    @Transactional
    public void delPost(Long id, String password) {
        //비밀번호 일치 확인
        log.info("param = {}, {}", id, password);
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty()) throw new NoSuchElementException();

        Post post = byId.get();

        //비밀번호 일치 확인 TODO 비밀번호만 가져와서 비교하면 조회 쿼리 단순화 가능
        if (passwordEncoder.matches(password, post.getPassword())) {
            postRepository.deleteById(id);
        } else throw new NoSuchElementException();
        //Long cnt = postRepository.countPostByPassword(id, password);
        //log.info("cnt = {}",cnt);
//        if(cnt<=0){ //없으면 예외
//            throw new NoSuchElementException();
//        }
        //있으면 삭제
    }

//    public List<Hashtag> getHashTags(Long id) {
//        //        log.info("size = {}",hastTags.size());
////        for (Hashtag hastTag : hastTags) {
////            log.info("hashTag = {}",hastTag.getName());
////        }
//        return hashtagRepository.findHashTagByPostId(id);
//    }

    public List<String> getHashTags(Long id) {
        //        log.info("size = {}",hastTags.size());
//        for (Hashtag hastTag : hastTags) {
//            log.info("hashTag = {}",hastTag.getName());
//        }
        return hashtagRepository.findHashTagByPostId(id);
    }

    public PostFormDto getEditingPost(Long id) {
        Optional<PostFormDto> editForm = postRepository.findPostByPassword(id);
        return editForm.orElseThrow();
    }


    public String getHashTagsString(Long id) {
        //List<Hashtag> hastTags = hashtagRepository.findHashTagByPostId(id);
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
        tagPostRepository.deleteByPostId(id);
        makeHashTags(obj.getHashtag(), curPost);
    }

    public boolean countByPassword(Long id, String password) {
        String encodePwd = postRepository.findPasswordById(id);
        return passwordEncoder.matches(password, encodePwd);
    }

    @Transactional //id = Post_id,
    public void addComment(Long id, CommentForm comment) {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.orElseThrow();
        Comment newComment = new Comment(comment.getWriter(),
                passwordEncoder.encode(comment.getPassword()),
                comment.getContent(), post);
        commentRepository.save(newComment);
    }

    @Transactional //TODO 비밀번호만 가져와서 비교하면 조회 쿼리 단순화 가능
    public void delComment(Long id, String password) {
        Optional<Comment> cmt = commentRepository.findById(id);
        Comment comment = cmt.orElseThrow();

        if (passwordEncoder.matches(password, comment.getPassword())) {
            comment.delComment();
        } else throw new NoSuchElementException();
    }

    public Long countAllComment() {
        return commentRepository.count();
    }

//    public List<CommentDto> getCommentDtos(List<Comment> comments) {
//        List<CommentDto> commentDtos = new LinkedList<>();
//        for (Comment comment : comments) {
//            CommentDto commentDto = new CommentDto(
//                    comment.getCommentId(),
//                    comment.getWriter(),
//                    comment.getContent(),
//                    comment.getCreated());
//            commentDtos.add(commentDto);
//        }
//        return commentDtos;
//    }

    public Page<CommentDto> getCommentDtos(Long id, Pageable pageable) {
        return commentRepository.findCommentByPostId(id, pageable);
    }

    public Long countAllPost() {
        return postRepository.count();
    }

    //TODO 겹치는 부분
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
            post.addLike();
            AnonymousLike newAddrLike = new AnonymousLike(ip, post);
            anonymousLikeRepository.save(newAddrLike);
            return true;
        }

        AnonymousLike postLiked = anonymousLike.get();
        if (timePassCheck(postLiked.getActionDate())) {//내가 추천을 눌렀지만 하루가 지나서 추천이 가능한 상황 => add
            postLiked.addSamePost();
            post.addLike();
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
            post.disLike();
            AnonymousLike newAddrLike = new AnonymousLike(ip, post);
            anonymousLikeRepository.save(newAddrLike);
            return true;
        }

        AnonymousLike postLiked = anonymousLike.get();
        if (timePassCheck(postLiked.getActionDate())) {//내가 추천을 눌렀지만 하루가 지나서 추천이 가능한 상황 => add
            postLiked.addSamePost();
            post.disLike();
            return true;
        }
        return false; //내가 추천을 눌렀고 하루가 안지나서 추천이 불가능한 상황 => ?
    }

    private boolean timePassCheck(LocalDateTime actionDate) {
        Duration duration = Duration.between(actionDate, LocalDateTime.now());
        // log.info("duration = {}",duration);
        //정해진 날짜보다 더 흘렀다.
        return duration.getSeconds() > MINUTE;
    }
}
