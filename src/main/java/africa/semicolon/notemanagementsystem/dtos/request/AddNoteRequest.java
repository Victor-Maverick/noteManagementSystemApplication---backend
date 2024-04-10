package africa.semicolon.notemanagementsystem.dtos.request;

import lombok.Data;

@Data
public class AddNoteRequest {
    private String title;
    private String content;
    private String author;
}
