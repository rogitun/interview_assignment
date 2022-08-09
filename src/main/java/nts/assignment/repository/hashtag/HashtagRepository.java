package nts.assignment.repository.hashtag;

import nts.assignment.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {


    Optional<Hashtag> findByName(String name);

    @Query("select h.name from Hashtag h " +
            "left join h.posts hp " +
            "where hp.post.postId = :id")
    List<String> findHashTagByPostId(@Param("id") Long id);
}
