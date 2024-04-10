package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponse {
    private String id;
    private String username;
    private LocalDateTime dateCreated;
    private boolean isLocked;
}
