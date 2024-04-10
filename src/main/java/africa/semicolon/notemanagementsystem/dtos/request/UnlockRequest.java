package africa.semicolon.notemanagementsystem.dtos.request;

import lombok.Data;

@Data
public class UnlockRequest {
    private String username;
    private String password;
}
