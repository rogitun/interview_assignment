package nts.assignment.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class EditForm {

    @NotEmpty
    @Length(min = 2,max = 16,message = "2~16자까지")
    private String title;

    @NotEmpty
    @Length(min = 2,max = 16,message = "2~16자까지")
    private String writer;

    @NotEmpty
    @Length(min = 2,max = 16,message = "2~16자까지")
    private String password;

    @NotEmpty
    @Length(max = 1024)
    private String content;
    private String hashtag;

    @NotEmpty
    private String pwd;
}
