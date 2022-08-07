package nts.assignment.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class MainPostDto {

    private Long post_id;
    private String title;
    private String writer;

    private String created;
    private String modified;

    private int viewed;
    private int likes;
    private boolean isNew;

    private int commentSize;

    @QueryProjection
    public MainPostDto(Long post_id, String title, String writer, LocalDateTime created, LocalDateTime modified, int viewed, int likes, boolean isNew, int commentSize) {
        this.post_id = post_id;
        this.title = title;
        this.writer = writer;
        this.created = created.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.modified = modified.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.viewed = viewed;
        this.likes = likes;
        this.isNew = calcDate(created);
        this.commentSize = commentSize;
    }

    public boolean calcDate(LocalDateTime created) {
        final int day = 86400;
        int second = LocalDateTime.now().getSecond() - created.getSecond();
        return (second / day) < 3;
    }
}
