package nts.assignment.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {

    @NotEmpty
    @Length(min = 2,max = 32,message = "2~32자까지 가능합니다.")
    private String writer;

    @NotEmpty
    @Length(min = 4,max = 16,message = "4~16자까지 가능합니다.")
    private String password;

    @NotEmpty
    @Length(min = 2,max = 512,message = "2~512자까지 가능합니다.")
    private String content;
}
