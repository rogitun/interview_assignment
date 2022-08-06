package nts.assignment.repository.hashtag;

import nts.assignment.domain.HashtagPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostRepository extends JpaRepository<HashtagPost,Long> {
}
