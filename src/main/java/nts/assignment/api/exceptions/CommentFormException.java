package nts.assignment.api.exceptions;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class CommentFormException extends RuntimeException {
    private HashMap<String,String> errorMap;

    public CommentFormException(HashMap<String, String> errorMap) {
      this.errorMap = errorMap;
    }
}
