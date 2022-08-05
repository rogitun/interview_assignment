package nts.assignment.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Hashtag {
    @Id @GeneratedValue
    private Long hashtag_id;

    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagPost> posts = new LinkedList<>();

}
