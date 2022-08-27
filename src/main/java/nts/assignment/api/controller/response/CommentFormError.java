package nts.assignment.api.controller.response;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
public class CommentFormError {
  private HttpStatus httpStatus;
  private int statusCode;
  private HashMap<String, String> errorMap;

  public CommentFormError(HttpStatus httpStatus, int statusCode, HashMap<String, String> errorMap) {
    this.httpStatus = httpStatus;
    this.statusCode = statusCode;
    this.errorMap = errorMap;
  }
}
