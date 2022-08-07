package nts.assignment.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommentDto {

    private Long id;
    private String writer;
    private String content;
    private String created;

    @QueryProjection
    public CommentDto(Long id,String writer, String content, LocalDateTime created) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.created = created.format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm"));
    }
}
