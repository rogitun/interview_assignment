package nts.assignment.api.exceptions;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class EditFormException extends RuntimeException {
    private HashMap<String,String> errorMap;

    public EditFormException(HashMap<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}
