package nts.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.controller.dto.Paging;
import nts.assignment.controller.dto.SearchCond;
import nts.assignment.domain.Post;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.service.CommentService;
import nts.assignment.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PostService postService;
    private final CommentService commentService;

    //TODO 페이징, 검색
    @GetMapping("/")
    public String main(Model model, Pageable pageable, SearchCond cond) {

        log.info("cond = {}",cond);

        Page<MainPostDto> allPost = postService.getAllPost(pageable,cond);
        Paging paging = new Paging(allPost.getTotalPages(), allPost.getNumber());
        log.info("paging numb = {} ",paging.getNumber());
        Long commentSize = commentService.countAllComment();
        Long postSize = postService.countAllPost();

        if(StringUtils.hasText(cond.getCategory())){
            model.addAttribute("searchCount",allPost.getTotalElements());
        }
        model.addAttribute("cond",cond);
        model.addAttribute("postCount", postSize);
        model.addAttribute("commentCount", commentSize);
        model.addAttribute("posts", allPost);
        model.addAttribute("paging", paging);
        return "/main/main.html";
    }

    @GetMapping("/2")
    public String main2() {
        return "/main/projects";
    }
}
