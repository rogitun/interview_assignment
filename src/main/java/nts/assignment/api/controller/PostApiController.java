package nts.assignment.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.api.controller.response.PostResponse;
import nts.assignment.api.exceptions.CommentFormException;
import nts.assignment.api.exceptions.EditFormException;
import nts.assignment.api.exceptions.PostFormException;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.form.CommentForm;
import nts.assignment.domain.form.EditForm;
import nts.assignment.domain.form.PostForm;
import nts.assignment.service.CommentService;
import nts.assignment.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

  private final PostService postService;
  private final CommentService commentService;

  @GetMapping("/post-liked/{like}")
  public ResponseEntity<PostResponse> getLikedPost(@PathVariable("like") int likes) {
    List<MainPostDto> likedPost = postService.getLikedPost(likes);
    String sb = likedPost.size() + "개의 데이터가 조회되었습니다.";
    PostResponse postResponse =
        new PostResponse(HttpStatus.OK, likedPost.size(), likedPost, sb);
    return new ResponseEntity<>(postResponse, postResponse.getStatus());
  }

  @PostMapping("/add-post")
  public ResponseEntity<String> addPost(
      @Validated @RequestBody PostForm postForm, BindingResult bindingResult) { // 변수명
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      HashMap<String, String> errorMap = new HashMap<>();
      for (FieldError fieldError : fieldErrors) {
        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
      }
      throw new PostFormException(errorMap);
    }

    log.info("input data : {} ", postForm);
    postService.addPost(postForm);
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
  public ResponseEntity<String> editPost(
      @PathVariable("id") Long id,
      @Validated @RequestBody EditForm editForm,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      HashMap<String, String> errorMap = new HashMap<>();
      for (FieldError fe : fieldErrors) {
        errorMap.put(fe.getField(), fe.getDefaultMessage());
      }
      throw new EditFormException(errorMap);
    }

    log.info("obj = {}", editForm);
    try {
      postService.editPost(id, editForm);
    } catch (NoSuchElementException e) {
      log.info("id = {}, obj = {}", id, editForm);
      e.getStackTrace();
      return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("변경되었습니다.", HttpStatus.OK);
  }

  // '/post/'+id + '/pwdCheck',
  @PostMapping("/post/{id}/pwdCheck")
  public ResponseEntity<String> pwdCheck(@PathVariable("id") Long id, String password) {
    log.info("password = {}", password);
    boolean flag = postService.countByPassword(id, password);
    if (flag) return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
  }
  @PostMapping("/post/{id}/addComment")
  public ResponseEntity<String> addComment(
      @PathVariable("id") Long id,
      @Validated @RequestBody CommentForm comment,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
          errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
      }
      throw new CommentFormException(errorMap);
    }

    try {
      commentService.addComment(id, comment);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("등록되었습니다.", HttpStatus.OK);
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
  public ResponseEntity<String> likePost(
      @PathVariable("id") Long id, HttpServletRequest req, String operation) {
    // Interceptor 처리
    // IP 주소 넘어온다.
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
