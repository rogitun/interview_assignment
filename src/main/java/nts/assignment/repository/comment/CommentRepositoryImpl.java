package nts.assignment.repository.comment;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.domain.QPost;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.QCommentDto;

import java.util.List;

import static nts.assignment.domain.QComment.comment;
import static nts.assignment.domain.QPost.post;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    //TODO 페이징 처리
    @Override
    public List<CommentDto> findCommentByPostId(Long id) {
        return queryFactory.select(new QCommentDto(comment.commentId,comment.writer, comment.content, comment.created))
                .from(comment)
                .join(comment.post,post)
                .where(post.postId.eq(id))
                .orderBy(comment.created.desc())
                .fetch();
    }
}
