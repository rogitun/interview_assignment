package nts.assignment.repository;

import nts.assignment.domain.AnonymousLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnonymousLikeRepository extends JpaRepository<AnonymousLike,Long> {

    @Query("select al from AnonymousLike al " +
            "join al.post p " +
            "where p.postId = :pId and " +
            "al.ipAddress = :IP")
    Optional<AnonymousLike> countByPostId(@Param("pId") Long id, @Param("IP") String addr);

}
