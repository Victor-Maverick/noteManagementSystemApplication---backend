package africa.semicolon.notemanagementsystem.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("user_table")
public class User {
    private String id;
    private String username;
    private String password;
    private boolean isLocked;
    private String email;
    private String phoneNumber;
    private List<Note> userNotes = new ArrayList<>();
}
