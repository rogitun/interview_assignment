package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PostService postService;

    //TODO 페이징, 검색
    @GetMapping("/")
    public String main(Model model, Pageable pageable){
        Page<MainPostDto> allPost = postService.getAllPost(pageable);
        Long commentSize = postService.countAllComment();
        model.addAttribute("commentCount",commentSize);
        model.addAttribute("postCount",allPost.getTotalElements());
        model.addAttribute("posts",allPost);
        return "/main/main.html";
    }

    @GetMapping("/2")
    public String main2(){
        return "/main/projects";
    }

}
