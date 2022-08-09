package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.controller.response.CommentResponse;
import nts.assignment.domain.Comment;
import nts.assignment.domain.Hashtag;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.CommentForm;
import nts.assignment.domain.form.EditForm;
import nts.assignment.domain.form.PostForm;
import nts.assignment.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/add-post")
    public String addPostForm() {
        return "/post/postForm";
    }

    @PostMapping("/add-post")
    @ResponseBody
    public ResponseEntity<String> addPost(@RequestBody PostForm obj) {
        log.info("input data : {} ", obj);
        postService.addPost(obj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/post/{id}")
//    public String viewPost(@PathVariable("id") Long id, Model model, HttpServletResponse resp) throws IOException {
//        SinglePostDto singlePost;
//        List<Hashtag> hashtags;
//        List<CommentDto> comments;
//        try {
//            //해쉬태그(String만) | 포스트(조인없이) | 댓글 (페이징 5개씩, postId랑 조인)
//            Post post = postService.getSinglePost(id);
//            singlePost = new SinglePostDto(post.getPostId(), post.getTitle(), post.getWriter(), post.getContent(), post.getCreated(), post.getModified()
//                    , post.getViewed(), post.getLikes());
//            comments = postService.getCommentDtos(post.getComments());
//            hashtags = postService.getHashTags(id);
//        } catch (NoSuchElementException e) {
//            log.info("e");
//            resp.sendError(HttpStatus.NOT_FOUND.value(), "No Such Content");
//            return "/error/404";
//        }
//        model.addAttribute("comments", comments);
//        model.addAttribute("post", singlePost);
//        model.addAttribute("hashtags", hashtags);
//        return "/post/post_detail";
//    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model, HttpServletResponse resp,
                           Pageable pageable) throws IOException {
        SinglePostDto singlePost;
        //List<Hashtag> hashtags;
        List<String> hashtags;
        Page<CommentDto> comments;
        try {
            //해쉬태그(String만) | 포스트(조인없이) | 댓글 (페이징 5개씩, postId랑 조인)
            singlePost = postService.getSinglePost(id);
            comments = postService.getCommentDtos(id, pageable);
            hashtags = postService.getHashTags(id);
        } catch (NoSuchElementException e) {
            log.info("e");
            resp.sendError(HttpStatus.NOT_FOUND.value(), "No Such Content");
            return "/error/404";
        }
        model.addAttribute("comments", comments);
        model.addAttribute("post", singlePost);
        model.addAttribute("hashtags", hashtags);
        return "/post/post_detail";
    }

    @GetMapping("/post/{id}/moreComment")
    @ResponseBody
    public ResponseEntity<CommentResponse> getMoreComment(@PathVariable("id") Long id, Pageable pageable) {
        //댓글을 불러왔을때 2개의 경우로 나눈다.
        //1. 가져올 댓글이 1개 이상인 경우 => 데이터 보낸다.
        //2. 가져올 댓글이 없는 경우 => size를 check하여 더 표시할 댓글이 없음을 알린다.
        Page<CommentDto> commentDtos = postService.getCommentDtos(id, pageable);
        CommentResponse commentResponse;
        if (commentDtos.getNumberOfElements() <= 0) {
            commentResponse = CommentResponse.builder()
                    .comments(null)
                    .size(0)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .statusCode(HttpStatus.NO_CONTENT.value()).build();
        } else {
            commentResponse = CommentResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .size(commentDtos.getNumberOfElements())
                    .comments(commentDtos.getContent())
                    .build();
        }
        return new ResponseEntity<>(commentResponse, commentResponse.getHttpStatus());
    }


    @PostMapping("/post/{id}/del")
    @ResponseBody
    public ResponseEntity<String> delPost(@PathVariable("id") Long id, String password) {
        try {
            postService.delPost(id, password);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/post/{id}/edit")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        try {
            String hashTag = postService.getHashTagsString(id);
            PostFormDto editingPost = postService.getEditingPost(id);
            model.addAttribute("post", editingPost);
            model.addAttribute("hashtag", hashTag);
        } catch (NoSuchElementException e) {
            return "/error/404";
        }
        return "/post/postEditForm";
    }

    @PostMapping("/post/{id}/edit")
    @ResponseBody
    public ResponseEntity<String> editPost(@PathVariable("id") Long id, @RequestBody EditForm obj) {
        log.info("obj = {}", obj);
        try {
            postService.editPost(id, obj);
        } catch (NoSuchElementException e) {
            log.info("id = {}, obj = {}", id, obj);
            e.getStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //'/post/'+id + '/pwdCheck',
    @PostMapping("/post/{id}/pwdCheck")
    @ResponseBody
    public ResponseEntity<String> pwdCheck(@PathVariable("id") Long id, String password) {
        log.info("password = {}", password);
        boolean flag = postService.countByPassword(id, password);
        if (flag) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post/{id}/addComment")
    @ResponseBody
    public ResponseEntity<String> addComment(@PathVariable("id") Long id, @RequestBody CommentForm comment) {
        try {
            postService.addComment(id, comment);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/{id}/del")
    @ResponseBody
    public ResponseEntity<String> delComment(@PathVariable("id") Long id, String password) {
        log.info("id = {} , password = {}", id, password);
        try {
            postService.delComment(id, password);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.info("id = {} , password = {}", id, password);
            e.getStackTrace();
            return new ResponseEntity<>("비밀번호를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/post/{id}/like")
    @ResponseBody
    public ResponseEntity<String> likePost(@PathVariable("id") Long id, HttpServletRequest req, String operation) {
        //Interceptor 처리
        //IP 주소 넘어온다.
        String ip = (String) req.getAttribute("IP");
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