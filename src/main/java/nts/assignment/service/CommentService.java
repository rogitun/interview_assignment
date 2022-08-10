package nts.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.Comment;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.form.CommentForm;
import nts.assignment.repository.comment.CommentRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional //id = Post_id,
    public void addComment(Long id, CommentForm comment) {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.orElseThrow();
        Comment newComment = new Comment(comment.getWriter(),
                passwordEncoder.encode(comment.getPassword()),
                comment.getContent(), post);
        commentRepository.save(newComment);
    }

    @Transactional
    public void delComment(Long id, String password) {
        //Optional<Comment> cmt = commentRepository.findById(id);
        String commentPassword = commentRepository.findPasswordById(id);
        // Comment comment = cmt.orElseThrow();
        if(commentPassword==null) throw new NoSuchElementException();

        if (passwordEncoder.matches(password, commentPassword)) {
            String changeWord = "삭제된 댓글입니다.";
            commentRepository.delComment(id,changeWord);
        } else throw new NoSuchElementException();
    }


    public Long countAllComment() {
        return commentRepository.count();
    }

    public Page<CommentDto> getCommentDtos(Long id, Pageable pageable) {
        return commentRepository.findCommentByPostId(id, pageable);
    }

}
