package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.Hashtag;
import nts.assignment.domain.dto.CommentForm;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.EditForm;
import nts.assignment.domain.form.PostForm;
import nts.assignment.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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


    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model, HttpServletResponse resp) throws IOException {
        SinglePostDto singlePost;
        List<Hashtag> hashtags;
        try {
            singlePost = postService.getSinglePost(id);
            hashtags = postService.getHashTags(id);
        } catch (NoSuchElementException e) {
            log.info("e");
            resp.sendError(HttpStatus.NOT_FOUND.value(), "No Such Content");
            return "/error/404";
        }
        model.addAttribute("post", singlePost);
        model.addAttribute("hashtags", hashtags);
        return "/post/post_detail";
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
    public ResponseEntity<String> addComment(@PathVariable("id") Long id, @RequestBody CommentForm comment) {
        try {
            postService.addComment(id, comment);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
