package nts.assignment.repository.comment;

import nts.assignment.domain.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    Page<CommentDto> findCommentByPostId(Long id, Pageable pageable);
}
