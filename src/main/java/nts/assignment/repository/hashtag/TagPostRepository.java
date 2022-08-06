package nts.assignment.repository.hashtag;

import nts.assignment.domain.HashtagPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagPostRepository extends JpaRepository<HashtagPost,Long> {


    //TODO batch
    @Modifying
    @Query("delete from HashtagPost hp " +
            "where hp.post.postId =:id")
    void deleteByPostId(@Param("id") Long id);
}
