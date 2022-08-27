package nts.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashtagPost is a Querydsl query type for HashtagPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashtagPost extends EntityPathBase<HashtagPost> {

    private static final long serialVersionUID = 1453632066L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHashtagPost hashtagPost = new QHashtagPost("hashtagPost");

    public final QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public QHashtagPost(String variable) {
        this(HashtagPost.class, forVariable(variable), INITS);
    }

    public QHashtagPost(Path<? extends HashtagPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHashtagPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHashtagPost(PathMetadata metadata, PathInits inits) {
        this(HashtagPost.class, metadata, inits);
    }

    public QHashtagPost(Class<? extends HashtagPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
    }

}

