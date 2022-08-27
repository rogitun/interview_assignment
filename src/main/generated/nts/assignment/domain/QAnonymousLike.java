package nts.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnonymousLike is a Querydsl query type for AnonymousLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnonymousLike extends EntityPathBase<AnonymousLike> {

    private static final long serialVersionUID = 790178778L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnonymousLike anonymousLike = new QAnonymousLike("anonymousLike");

    public final DateTimePath<java.time.LocalDateTime> actionDate = createDateTime("actionDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> anonymousLikeId = createNumber("anonymousLikeId", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final QPost post;

    public QAnonymousLike(String variable) {
        this(AnonymousLike.class, forVariable(variable), INITS);
    }

    public QAnonymousLike(Path<? extends AnonymousLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnonymousLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnonymousLike(PathMetadata metadata, PathInits inits) {
        this(AnonymousLike.class, metadata, inits);
    }

    public QAnonymousLike(Class<? extends AnonymousLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
    }

}

