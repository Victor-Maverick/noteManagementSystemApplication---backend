package africa.semicolon.notemanagementsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteNoteRequest {
    private String title;
    private String author;
}
