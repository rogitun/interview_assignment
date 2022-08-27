package nts.assignment.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nts.assignment.domain.dto.QCommentDto is a Querydsl Projection type for CommentDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentDto extends ConstructorExpression<CommentDto> {

    private static final long serialVersionUID = 123394809L;

    public QCommentDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> created) {
        super(CommentDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class}, id, writer, content, created);
    }

}

