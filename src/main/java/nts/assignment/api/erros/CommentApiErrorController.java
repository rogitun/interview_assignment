package nts.assignment.api.erros;

import nts.assignment.api.controller.response.CommentFormError;
import nts.assignment.api.exceptions.CommentFormException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentApiErrorController {

  @ExceptionHandler()
  public ResponseEntity<CommentFormError> commentFormError(CommentFormException e) {
    CommentFormError commentFormError =
        new CommentFormError(
            HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.getErrorMap());
    return new ResponseEntity<>(commentFormError, commentFormError.getHttpStatus());
  }
}
