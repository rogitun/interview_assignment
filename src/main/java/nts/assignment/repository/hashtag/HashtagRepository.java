package nts.assignment.repository.hashtag;

import nts.assignment.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {


    Optional<Hashtag> findByName(String name);
}
