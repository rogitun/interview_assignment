package nts.assignment.api.controller.response;

import lombok.Data;
import nts.assignment.domain.dto.MainPostDto;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PostResponse {

    private HttpStatus status;
    private int count;
    private String message;
    private List<MainPostDto> likedPosts;

    public PostResponse(HttpStatus status, int count, List<MainPostDto> likedPosts,String msg) {
        this.status = status;
        this.count = count;
        this.likedPosts = likedPosts;
        this.message = msg;
    }
}
