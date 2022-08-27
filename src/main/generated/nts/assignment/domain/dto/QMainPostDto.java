package nts.assignment.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nts.assignment.domain.dto.QMainPostDto is a Querydsl Projection type for MainPostDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainPostDto extends ConstructorExpression<MainPostDto> {

    private static final long serialVersionUID = 445891661L;

    public QMainPostDto(com.querydsl.core.types.Expression<Long> post_id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<java.time.LocalDateTime> created, com.querydsl.core.types.Expression<java.time.LocalDateTime> modified, com.querydsl.core.types.Expression<Integer> viewed, com.querydsl.core.types.Expression<Integer> likes, com.querydsl.core.types.Expression<Boolean> isNew, com.querydsl.core.types.Expression<Integer> commentSize) {
        super(MainPostDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, int.class, int.class, boolean.class, int.class}, post_id, title, writer, created, modified, viewed, likes, isNew, commentSize);
    }

}

