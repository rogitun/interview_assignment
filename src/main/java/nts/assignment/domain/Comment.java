package nts.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    private Long commentId;

    private String writer;

    private String password;

    private String content;

    private LocalDateTime created;

    private LocalDateTime modified;

    private int likes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void addComment(Post post){
        post.getComments().add(this);
        this.post = post;
    }

    public Comment(String writer, String password, String content) {
        this.writer = writer;
        this.password = password;
        this.content = content;
    }
}
