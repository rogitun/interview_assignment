package nts.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 155226538L;

    public static final QPost post = new QPost("post");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> created = createDateTime("created", java.time.LocalDateTime.class);

    public final ListPath<HashtagPost, QHashtagPost> hashtags = this.<HashtagPost, QHashtagPost>createList("hashtags", HashtagPost.class, QHashtagPost.class, PathInits.DIRECT2);

    public final BooleanPath isNew = createBoolean("isNew");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modified = createDateTime("modified", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewed = createNumber("viewed", Integer.class);

    public final StringPath writer = createString("writer");

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

