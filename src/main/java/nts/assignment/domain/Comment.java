package nts.assignment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    private String writer;

    private String password;

    private String content;

    private LocalDateTime created;

    private LocalDateTime modified;

    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
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
