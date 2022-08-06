package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.dto.SinglePostDto;
import nts.assignment.domain.form.PostForm;
import nts.assignment.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/add-post")
    public String addPostForm(){
        return "/form/postForm";
    }

    @PostMapping("/add-post")
    public String addPost(PostForm postForm){
        log.info("input data : {} ", postForm);
        postService.addPost(postForm);
        return "redirect:/";
    }


    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model, HttpServletResponse resp) throws IOException {
        try {
            SinglePostDto singlePost = postService.getSinglePost(id);
            model.addAttribute("post",singlePost);
        }catch (NoSuchElementException e){
            resp.sendError(HttpStatus.NO_CONTENT.value(),"No Such Content");
        }
        return "/post/post_detail";
    }
}
