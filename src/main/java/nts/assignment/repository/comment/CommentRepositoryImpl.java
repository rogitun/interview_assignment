package nts.assignment.repository.comment;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.domain.QPost;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.domain.dto.QCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static nts.assignment.domain.QComment.comment;
import static nts.assignment.domain.QPost.post;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    private final int PER_PAGE = 5;

    //TODO 페이징 처리
    @Override
    public Page<CommentDto> findCommentByPostId(Long id, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page,PER_PAGE);

        List<CommentDto> data = queryFactory.select(new QCommentDto(comment.commentId, comment.writer, comment.content, comment.created))
                .from(comment)
                .join(comment.post, post)
                .where(post.postId.eq(id))
                .orderBy(comment.created.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory.select(comment.count())
                .from(comment)
                .join(comment.post, post)
                .where(post.postId.eq(id));

        return PageableExecutionUtils.getPage(data,pageRequest,countQuery::fetchOne);
    }
}
