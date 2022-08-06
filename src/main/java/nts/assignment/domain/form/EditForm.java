package nts.assignment.domain.form;

import lombok.Data;

@Data
public class EditForm {

    private String title;
    private String writer;
    private String password;
    private String content;
    private String hashtag;
    private String pwd;
}
