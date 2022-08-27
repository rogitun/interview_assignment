package nts.assignment.api.exceptions;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class PostFormException extends RuntimeException{
    private HashMap<String,String> errorMap;

    public PostFormException(HashMap<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}
