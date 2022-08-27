package nts.assignment.repository.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nts.assignment.controller.dto.SearchCond;
import nts.assignment.domain.Post;
import nts.assignment.domain.QPost;
import nts.assignment.domain.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static nts.assignment.domain.QComment.comment;
import static nts.assignment.domain.QHashtag.hashtag;
import static nts.assignment.domain.QHashtagPost.hashtagPost;
import static nts.assignment.domain.QPost.post;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final int CONTENT_PER_PAGE = 10;

    @Override
    public Page<MainPostDto> getAllPost(Pageable pageable, SearchCond cond) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, CONTENT_PER_PAGE);
        List<MainPostDto> data;
        JPAQuery<Long> countQuery;
        //해시태그가 있는 경우 -> 따로 조인
        if (StringUtils.hasText(cond.getCategory()) && cond.getCategory().equals("hashtag")) {
            data = queryFactory.select(new QMainPostDto(post.postId, post.title, post.writer,
                    post.created, post.modified, post.viewed,
                    post.likes, post.isNew, post.comments.size())).distinct()
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .from(post)
                    .leftJoin(post.hashtags, hashtagPost)
                    .leftJoin(hashtagPost.hashtag, hashtag)
                    .where(hashtag.name.contains(cond.getSearch()))
                    .orderBy(post.created.desc())
                    .fetch();

            countQuery = queryFactory.select(post.count())
                    .from(post)
                    .leftJoin(post.hashtags, hashtagPost)
                    .leftJoin(hashtagPost.hashtag, hashtag)
                    .where(hashtag.name.contains(cond.getSearch()));

        } else {
            data = queryFactory.select(new QMainPostDto(post.postId, post.title, post.writer,
                            post.created, post.modified, post.viewed,
                            post.likes, post.isNew, post.comments.size()))
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .from(post)
                    .where(isAnythingContains(cond))
                    .orderBy(post.created.desc())
                    .fetch();

            countQuery = queryFactory.select(post.count())
                    .where(isAnythingContains(cond))
                    .from(post);
        }

        return PageableExecutionUtils.getPage(data, pageRequest, countQuery::fetchOne);
    }

    private BooleanBuilder isAnythingContains(SearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(cond.getCategory())) {
            if (cond.getCategory().equals("title")) builder.or(post.title.contains(cond.getSearch()));
            else if (cond.getCategory().equals("content")) builder.or(post.content.contains(cond.getSearch()));
            else if (cond.getCategory().equals("writer")) builder.or(post.writer.contains(cond.getSearch()));
        }
        return builder;
    }

    @Override
    public Optional<SinglePostDto> getSinglePost(Long id) {
        SinglePostDto singlePost = queryFactory.select(new QSinglePostDto(post.postId, post.title, post.writer, post.content, post.created, post.modified, post.viewed, post.likes))
                .from(post)
                .where(post.postId.eq(id))
                .fetchOne();
        return Optional.ofNullable(singlePost);
    }

    @Override
    public Optional<PostFormDto> findPostByPassword(Long id) {
        PostFormDto postFormDto = queryFactory.select(new QPostFormDto(post.postId, post.title, post.content, post.writer, post.password))
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

    @Override
    public Optional<Post> findPostWithComments(Long id) {
        Post post = queryFactory.select(QPost.post)
                .from(QPost.post)
                .leftJoin(QPost.post.comments, comment).fetchJoin()
                .where(QPost.post.postId.eq(id))
                .fetchOne();


        return Optional.ofNullable(post);
    }

    @Override
    public List<Long> countMain() {
        queryFactory.select(post.postId.count(), comment.commentId.count())
                .from(post, comment)
                .fetch();

        return null;
    }

    @Override
    public List<MainPostDto> getLikedPosts(int likes) {
        //좋아요 5개 이상 게시글 조회

        return queryFactory.select(new QMainPostDto(post.postId, post.title, post.writer, post.created, post.modified, post.viewed, post.likes, post.isNew, post.comments.size()))
                .from(post)
                .where(isUpperLiked(likes))
                .orderBy(post.likes.desc())
                .fetch();
    }

    private BooleanBuilder isUpperLiked(int likes){
        BooleanBuilder builder = new BooleanBuilder();
        return builder.and(post.likes.goe(likes));
    }

}
