package nts.assignment.api.erros;

import nts.assignment.api.controller.response.EditFormError;
import nts.assignment.api.controller.response.PostFormError;
import nts.assignment.api.exceptions.EditFormException;
import nts.assignment.api.exceptions.PostFormException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostApiErrorController {

    @ExceptionHandler
    public ResponseEntity<PostFormError> postFormError(PostFormException e){
        PostFormError postFormError = new PostFormError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.getErrorMap());
        return new ResponseEntity<>(postFormError,postFormError.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<EditFormError> editFormError(EditFormException e){
        EditFormError editFormError = new EditFormError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.getErrorMap());
        return new ResponseEntity<>(editFormError,editFormError.getHttpStatus());
    }
}
