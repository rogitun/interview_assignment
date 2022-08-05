package nts.assignment.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Post {

    @Id @GeneratedValue
    private Long postId;

    private String title;

    private String writer;

    private String password;

    private String content;

    private LocalDateTime created;
    private LocalDateTime modified;

    private int viewed;
    private int likes;
    private int isNew;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "post")
    private List<HashtagPost> hashtags = new LinkedList<>();
}
