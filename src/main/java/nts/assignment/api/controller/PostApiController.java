package nts.assignment.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.form.CommentForm;
import nts.assignment.domain.form.EditForm;
import nts.assignment.domain.form.PostForm;
import nts.assignment.service.CommentService;
import nts.assignment.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/add-post")
    public ResponseEntity<String> addPost(@RequestBody PostForm obj) {
        log.info("input data : {} ", obj);
        postService.addPost(obj);
        return new ResponseEntity<>("등록되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/post/{id}/del")
    public ResponseEntity<String> delPost(@PathVariable("id") Long id, String password) {
        try {
            postService.delPost(id, password);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/post/{id}/edit")
    public ResponseEntity<String> editPost(@PathVariable("id") Long id, @RequestBody EditForm obj) {
        log.info("obj = {}", obj);
        try {
            postService.editPost(id, obj);
        } catch (NoSuchElementException e) {
            log.info("id = {}, obj = {}", id, obj);
            e.getStackTrace();
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("변경되었습니다.", HttpStatus.OK);
    }


    //'/post/'+id + '/pwdCheck',
    @PostMapping("/post/{id}/pwdCheck")
    public ResponseEntity<String> pwdCheck(@PathVariable("id") Long id, String password) {
        log.info("password = {}", password);
        boolean flag = postService.countByPassword(id, password);
        if (flag) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post/{id}/addComment")
    public ResponseEntity<String> addComment(@PathVariable("id") Long id, @RequestBody CommentForm comment) {
        try {
            commentService.addComment(id, comment);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("게시글이 존재하지 않습니다.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("등록되었습니다.",HttpStatus.OK);
    }

    @PostMapping("/comment/{id}/del")
    public ResponseEntity<String> delComment(@PathVariable("id") Long id, String password) {
        log.info("id = {} , password = {}", id, password);
        try {
            commentService.delComment(id, password);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.info("id = {} , password = {}", id, password);
            e.getStackTrace();
            return new ResponseEntity<>("비밀번호를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/post/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable("id") Long id, HttpServletRequest req, String operation) {
        //Interceptor 처리
        //IP 주소 넘어온다.
        String ip = req.getRemoteAddr();
        // String ip = (String) req.getAttribute("IP");

        if (operation.equals("add")) {
            try {
                boolean flag = postService.likePost(id, ip);
                if (flag) return new ResponseEntity<>("추천되었습니다.", HttpStatus.OK);
                else return new ResponseEntity<>("이미 추천/비추천 게시글입니다.", HttpStatus.FORBIDDEN);
            } catch (NoSuchElementException e) {
                log.info("게시글 삭제되어 추천 불가능");
                return new ResponseEntity<>("삭제된 게시글입니다.", HttpStatus.BAD_REQUEST);
            }
        } else if (operation.equals("sub")) {
            try {
                boolean flag = postService.disLikePost(id, ip);
                if (flag) return new ResponseEntity<>("비추천 되었습니다.", HttpStatus.OK);
                else return new ResponseEntity<>("이미 추천/비추천 한 게시글입니다.", HttpStatus.FORBIDDEN);
            } catch (NoSuchElementException e) {
                log.info("게시글 삭제되어 추천 불가능");
                return new ResponseEntity<>("삭제된 게시글입니다.", HttpStatus.BAD_REQUEST);
            }
        } else return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
    }
}
