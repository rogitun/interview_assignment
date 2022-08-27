package nts.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashtag is a Querydsl query type for Hashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashtag extends EntityPathBase<Hashtag> {

    private static final long serialVersionUID = -237130110L;

    public static final QHashtag hashtag = new QHashtag("hashtag");

    public final NumberPath<Long> hashtag_id = createNumber("hashtag_id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<HashtagPost, QHashtagPost> posts = this.<HashtagPost, QHashtagPost>createList("posts", HashtagPost.class, QHashtagPost.class, PathInits.DIRECT2);

    public QHashtag(String variable) {
        super(Hashtag.class, forVariable(variable));
    }

    public QHashtag(Path<? extends Hashtag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashtag(PathMetadata metadata) {
        super(Hashtag.class, metadata);
    }

}

