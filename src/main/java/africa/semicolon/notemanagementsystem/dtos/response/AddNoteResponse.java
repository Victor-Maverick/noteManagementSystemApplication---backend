package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AddNoteResponse {
    private String title;
    private LocalDateTime dateCreated;
}
