package nts.assignment.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nts.assignment.domain.dto.QSinglePostDto is a Querydsl Projection type for SinglePostDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSinglePostDto extends ConstructorExpression<SinglePostDto> {

    private static final long serialVersionUID = -1156814562L;

    public QSinglePostDto(com.querydsl.core.types.Expression<Long> post_id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> created, com.querydsl.core.types.Expression<java.time.LocalDateTime> modified, com.querydsl.core.types.Expression<Integer> viewed, com.querydsl.core.types.Expression<Integer> likes) {
        super(SinglePostDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, int.class, int.class}, post_id, title, writer, content, created, modified, viewed, likes);
    }

}

