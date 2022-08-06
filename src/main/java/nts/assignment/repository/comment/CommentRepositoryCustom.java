package nts.assignment.repository.comment;

import nts.assignment.domain.dto.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDto> findCommentByPostId(Long id);
}
