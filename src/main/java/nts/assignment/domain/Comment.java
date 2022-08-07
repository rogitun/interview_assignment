package nts.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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
    @JoinColumn(name = "postId")
    private Post post;

    public Comment(String writer, String password, String content,Post post) {
        this.writer = writer;
        this.password = password;
        this.content = content;
        created = LocalDateTime.now();
        modified = created;
        post.getComments().add(this);
        this.post = post;
    }

    public void delComment(){
        this.content = "삭제된 댓글입니다.";
        modified = LocalDateTime.now();
    }
}
