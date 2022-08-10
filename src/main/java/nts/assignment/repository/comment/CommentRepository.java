package nts.assignment.repository.comment;

import nts.assignment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {

    @Query("select c.password from Comment c " +
            "where c.commentId = :id")
    String findPasswordById(@Param("id") Long id);

    @Modifying
    @Query("update Comment c " +
            "set c.content = :word " +
            "where c.id =:id")
    void delComment(@Param("id") Long id,@Param("word") String changeWord);

    @Modifying
    @Query("delete from Comment c " +
            "where c.post.postId = :id")
    void deleteByPostId(@Param("id") Long id);
}
