package nts.assignment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HashtagPost {

    @Id @GeneratedValue
    @Column(name = "hashtag_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public HashtagPost(Hashtag hashtag, Post post) {
        this.hashtag = hashtag;
        this.post = post;
        post.getHashtags().add(this);
        hashtag.getPosts().add(this);
    }
}
