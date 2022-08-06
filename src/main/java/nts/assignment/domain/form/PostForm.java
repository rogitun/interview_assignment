package nts.assignment.domain.form;

import lombok.Data;

@Data
public class PostForm {

    private String title;
    private String writer;
    private String password;
    private String content;
    private String hashtag;
}
