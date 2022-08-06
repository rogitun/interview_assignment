package nts.assignment.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class SinglePostDto {

    private Long post_id;
    private String title;
    private String writer;
    private String content;


    private String created;
    private String modified;

    private int viewed;
    private int likes;
    private int isNew;

    @QueryProjection
    public SinglePostDto(Long post_id, String title, String writer, String content, LocalDateTime created, LocalDateTime modified, int viewed, int likes, int isNew) {
        this.post_id = post_id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.created = created.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.modified = modified.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.viewed = viewed;
        this.likes = likes;
        this.isNew = isNew;
    }
}
