package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditNoteResponse {
    private String id;
    private String newTitle;
    private String newContent;

}
