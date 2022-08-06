package nts.assignment.repository.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.domain.Post;
import nts.assignment.domain.QPost;
import nts.assignment.domain.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static nts.assignment.domain.QPost.post;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public Optional<PostFormDto> findPostByPassword(Long id) {
        PostFormDto postFormDto = queryFactory.select(new QPostFormDto(post.postId,post.title, post.content, post.writer,post.password))
                .from(post)
                .where(post.postId.eq(id))
                .fetchOne();

        return Optional.ofNullable(postFormDto);
    }

    @Override
    public Optional<Post> findPostByCredential(Long id, String pwd) {
        Post post = queryFactory.select(QPost.post)
                .from(QPost.post)
                .where(QPost.post.postId.eq(id), QPost.post.password.eq(pwd))
                .fetchOne();

        return Optional.ofNullable(post);
    }

}
