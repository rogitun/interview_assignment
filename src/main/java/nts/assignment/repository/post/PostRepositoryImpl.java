package nts.assignment.repository.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.QMainPostDto;
import nts.assignment.domain.dto.QSinglePostDto;
import nts.assignment.domain.dto.SinglePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static nts.assignment.domain.QPost.post;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MainPostDto> getAllPost(Pageable pageable) {
        List<MainPostDto> data = queryFactory.select(new QMainPostDto(post.postId, post.title, post.writer,
                        post.created, post.modified, post.viewed,
                        post.likes, post.isNew, post.comments.size()))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(data,pageable,countQuery::fetchOne);
    }

    @Override
    public Optional<SinglePostDto> getSinglePost(Long id) {
        SinglePostDto singlePost = queryFactory.select(new QSinglePostDto(post.postId, post.title, post.writer, post.content, post.created, post.modified, post.viewed, post.likes, post.isNew))
                .from(post)
                .where(post.postId.eq(id))
                .fetchOne();
        return Optional.ofNullable(singlePost);
    }
}
