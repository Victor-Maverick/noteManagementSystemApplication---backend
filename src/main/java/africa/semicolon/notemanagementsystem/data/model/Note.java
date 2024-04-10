package africa.semicolon.notemanagementsystem.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("notes_table")
public class Note {
    @Id
    private String id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
