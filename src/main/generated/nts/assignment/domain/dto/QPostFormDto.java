package nts.assignment.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nts.assignment.domain.dto.QPostFormDto is a Querydsl Projection type for PostFormDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostFormDto extends ConstructorExpression<PostFormDto> {

    private static final long serialVersionUID = 1579800802L;

    public QPostFormDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> password) {
        super(PostFormDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class}, postId, title, content, writer, password);
    }

}

