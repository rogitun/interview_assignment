package nts.assignment.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class PostForm {

    @NotEmpty
    @Length(min = 1,max = 32,message = "4~32자까지 가능합니다.")
    private String title;

    @NotEmpty
    @Length(min = 2,max = 16,message = "2~16자까지 가능합니다.")
    private String writer;

    @NotEmpty
    @Length(min = 4,max = 16,message = "4~16자까지 가능합니다")
    private String password;

    @NotEmpty
    private String content;
    private String hashtag;
}
