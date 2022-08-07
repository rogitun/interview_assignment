package nts.assignment.domain.form;

import lombok.Data;

@Data
public class CommentForm {

    private String writer;
    private String password;
    private String content;
}
