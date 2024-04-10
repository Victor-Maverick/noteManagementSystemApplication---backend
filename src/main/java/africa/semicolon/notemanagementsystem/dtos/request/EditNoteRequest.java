package africa.semicolon.notemanagementsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditNoteRequest {
    private String title;
    private String newTitle;
    private String newContent;
    private String author;
}
