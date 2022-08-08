package nts.assignment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "anonymous_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnonymousLike {

    @Id @GeneratedValue
    @Column(name = "anonymous_like_id")
    private Long anonymousLikeId;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime actionDate;

    public AnonymousLike(String ipAddress,Post post) {
        this.ipAddress = ipAddress;
        this.post = post;
        actionDate = LocalDateTime.now();

    }

    public void addPost(Post post){
        this.post = post;
        actionDate = LocalDateTime.now();
    }

    public void addSamePost(){
        actionDate = LocalDateTime.now();
    }
}
