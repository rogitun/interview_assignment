package nts.assignment.repository.post;

import nts.assignment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("select count(p) from Post p " +
            "where p.postId = :id and " +
            "p.password = :pwd")
    Long countPostByPassword(@Param("id") Long id, @Param("pwd") String password);

    @Query("select p.password from Post p " +
            "where p.postId = :id")
    String findPasswordById(@Param("id") Long id);

}
