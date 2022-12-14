package nts.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    private String title;

    private String writer;

    private String password;

    private String content;

    private LocalDateTime created;
    private LocalDateTime modified;

    private int viewed;
    private int likes;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<HashtagPost> hashtags = new LinkedList<>();

    public void editPost(String title,String content,String password,String writer){
        this.title = title;
        this.content = content;
        this.password =password;
        this.writer = writer;
        modified = LocalDateTime.now();
        hashtags.clear();
    }

    public void addLike() {
        this.likes++;
    }

    public void disLike() { this.likes--;
    }
}
