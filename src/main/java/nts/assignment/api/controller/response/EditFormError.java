package nts.assignment.api.controller.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class EditFormError {

    private HttpStatus httpStatus;
    private int statusCode;
    private HashMap<String,String> errorMap;

    public EditFormError(HttpStatus httpStatus, int statusCode, HashMap<String, String> errorMap) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.errorMap = errorMap;
    }
}
