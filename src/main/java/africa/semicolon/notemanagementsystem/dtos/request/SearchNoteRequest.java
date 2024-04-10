package africa.semicolon.notemanagementsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchNoteRequest {
    private String title;
    private String author;
}
