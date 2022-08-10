package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.service.CommentService;
import nts.assignment.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final CommentService commentService;

    @GetMapping("/add-post")
    public String addPostForm() {
        return "/post/postForm";
    }

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
            comments = commentService.getCommentDtos(id, pageable);
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
}