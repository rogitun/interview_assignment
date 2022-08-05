package nts.assignment.repository;

import nts.assignment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p " +
            "join fetch p.comments c " +
            "where p.id =:id")
    Post getPost(@Param("id") Long id);

}
