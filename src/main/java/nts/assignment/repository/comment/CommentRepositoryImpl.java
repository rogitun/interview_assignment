package nts.assignment.repository.comment;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.QCommentDto;

import java.util.List;

import static nts.assignment.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    //TODO 페이징 처리
    @Override
    public List<CommentDto> findCommentByPostId(Long id) {
        return queryFactory.select(new QCommentDto(comment.writer, comment.content, comment.created))
                .from(comment)
                .orderBy(comment.created.desc())
                .fetch();
    }
}
