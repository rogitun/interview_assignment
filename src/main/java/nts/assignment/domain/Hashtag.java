package nts.assignment.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {
    @Id @GeneratedValue
    private Long hashtag_id;

    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagPost> posts = new LinkedList<>();

    public Hashtag(String name) {
        this.name = name;
    }
}
