package nts.assignment.api.controller.response;

import lombok.Builder;
import lombok.Data;
import nts.assignment.domain.dto.CommentDto;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class CommentResponse {

    private int size;
    private List<CommentDto> comments;
    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
}
