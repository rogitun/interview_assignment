package nts.assignment.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostFormDto {

    private Long postId;
    private String title;
    private String content;
    private String writer;
    private String password;

    @QueryProjection
    public PostFormDto(Long postId,String title, String content, String writer,String password) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }
}
